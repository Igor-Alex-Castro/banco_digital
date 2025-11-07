package com.br.bancodigital.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.br.bancodigital.dto.ContaDto;
import com.br.bancodigital.dto.SalvarChavePixDto;
import com.br.bancodigital.dto.TransferenciaDTO;
import com.br.bancodigital.enuns.TipoCliente;
import com.br.bancodigital.enuns.TipoConta;
import com.br.bancodigital.enuns.TipoPix;
import com.br.bancodigital.exceptions.BusinessException;
import com.br.bancodigital.exceptions.ResourceNotFoundException;
import com.br.bancodigital.models.Cliente;
import com.br.bancodigital.models.Conta;
import com.br.bancodigital.models.ContaCorrente;
import com.br.bancodigital.models.ContaPoupanca;
import com.br.bancodigital.repositories.ClienteRepository;
import com.br.bancodigital.repositories.ContaCorrenteRepository;
import com.br.bancodigital.repositories.ContaPoupRepository;
import com.br.bancodigital.repositories.ContaRepository;
import com.br.bancodigital.utils.Utils;

@Service
public class ContaService {

	private final ContaRepository contaRepository;
	private final ClienteRepository clienteRepository;
	private final ContaPoupRepository contaPoupRepository;
	private final ContaCorrenteRepository contaCorrenteRepository;

	public ContaService(ContaRepository contaRepository, ClienteRepository clienteRepository,
			ContaPoupRepository contaPoupRepository, ContaCorrenteRepository contaCorrenteRepository

	) {
		// TODO Auto-generated constructor stub
		this.contaRepository = contaRepository;
		this.clienteRepository = clienteRepository;
		this.contaCorrenteRepository = contaCorrenteRepository;
		this.contaPoupRepository = contaPoupRepository;
	}

	public Conta salvar(ContaDto contaDto) {
		Cliente cliente = clienteRepository.findById(contaDto.clienteId())
				.orElseThrow(() -> new ResourceNotFoundException("Este cliente não existe"));

		Conta conta = new Conta();

		if (isContaValida(contaDto, cliente)) {
			contaRepository.save(popularConta(contaDto, conta, cliente));
		}
		;

		if (contaDto.tipoConta() == TipoConta.POUPANCA) {
			contaPoupRepository.save(salvarContaTipoPouanca(cliente, conta));
		} else {
			contaCorrenteRepository.save(salvarContaTipoCorrente(cliente, conta));
		}

		return conta;
	}

	private ContaCorrente salvarContaTipoCorrente(Cliente cliente, Conta conta) {
		// TODO Auto-generated method stub
		ContaCorrente contaCorrente = new ContaCorrente();
		contaCorrente.setSaldo(new BigDecimal(0.00));

		if (cliente.getTipoCliente() == TipoCliente.COMUM) {
			contaCorrente.setTaxaMensal(new BigDecimal(12.00));
		}

		if (cliente.getTipoCliente() == TipoCliente.SUPER) {
			contaCorrente.setTaxaMensal(new BigDecimal(8.00));
		}

		if (cliente.getTipoCliente() == TipoCliente.PREMIUM) {
			contaCorrente.setTaxaMensal(new BigDecimal(0.00));
		}

		contaCorrente.setConta(conta);
		conta.setContaCorrente(contaCorrente);

		return contaCorrente;
	}

	private ContaPoupanca salvarContaTipoPouanca(Cliente cliente, Conta conta) {
		// TODO Auto-generated method stub
		ContaPoupanca contaPoupanca = new ContaPoupanca();

		contaPoupanca.setSaldo(new BigDecimal(0.00));

		if (cliente.getTipoCliente() == TipoCliente.COMUM) {

			contaPoupanca.setTaxaRendaAnual(new BigDecimal(0.5));
		}

		if (cliente.getTipoCliente() == TipoCliente.SUPER) {
			contaPoupanca.setTaxaRendaAnual(new BigDecimal(0.7));
		}

		if (cliente.getTipoCliente() == TipoCliente.PREMIUM) {
			contaPoupanca.setTaxaRendaAnual(new BigDecimal(0.9));
		}

		contaPoupanca.setConta(conta);
		conta.setContaPoupanca(contaPoupanca);

		return contaPoupanca;
	}

	private Conta popularConta(ContaDto contaDto, Conta conta, Cliente cliente) {
		// TODO Auto-generated method stub
		conta.setCliente(cliente);
		conta.setConta(contaDto.conta());
		conta.setAgencia(contaDto.agencia());
		conta.setTipoConta(contaDto.tipoConta());

		return conta;
	}

	private boolean isContaValida(ContaDto contaDto, Cliente cliente) {
		// TODO Auto-generated method stub
		if (contaRepository.existsByConta(contaDto.conta())) {
			throw new BusinessException("Esta conta já existe");
		}

		if (contaRepository.countByClienteIdAndTipoConta(cliente.getId(), contaDto.tipoConta()) >= 5) {
			throw new BusinessException("Limite de 5 contas do tipo " + contaDto.tipoConta() + " para o número "
					+ contaDto.conta() + " atingido.");
		}

		return true;
	}

	public List<ContaDto> obterContasPorIdCliente(Long idCliente) {
		// TODO Auto-generated method stub
		List<Conta> contas = contaRepository.findByClienteId(idCliente);

		List<ContaDto> constasDto = contas.stream().map(ContaDto::new).toList();

		return constasDto;
	}

	public ContaDto obterContasPorId(Long contaId) {
		// TODO Auto-generated method stub
		Conta conta = contaRepository.findById(contaId)
				.orElseThrow(() -> new BusinessException("Não tem essa conta na base"));

		return new ContaDto(conta);
	}

	public void deletePorId(Long id) {
		// TODO Auto-generated method stub
		contaRepository.findById(id).orElseThrow(() -> new BusinessException("Não tem essa conta na base"));

		contaRepository.deleteById(id);
	}

	public ContaDto transferencia(Long contaId, TransferenciaDTO transferenciaDTO) {
		// TODO Auto-generated method stub
		Conta contaAtual = contaRepository.findById(contaId)
				.orElseThrow(() -> new BusinessException("Não tem essa conta na base"));

		Conta contaDestino = contaRepository.findById(transferenciaDTO.idDestino())
				.orElseThrow(() -> new BusinessException("Não tem essa conta na base"));

		return movimentacaoTransferencia(contaAtual, contaDestino, transferenciaDTO);
	}

	public ContaDto movimentacaoTransferencia(Conta contaAtual, Conta contaDestino, TransferenciaDTO transferenciaDTO) {

		if (contaAtual.getId() != null && contaAtual.equals(contaDestino)) {
			throw new BusinessException("Não é possivel transferir dinheiro para a mesma conta");
		}

		BigDecimal valorContaAtual = contaAtual.getTipoConta() == TipoConta.POUPANCA
				? contaAtual.getContaPoupanca().getSaldo()
				: contaAtual.getContaCorrente().getSaldo();

		if (transferenciaDTO.valorTransferir().compareTo(BigDecimal.ZERO) == -1
				|| transferenciaDTO.valorTransferir().compareTo(BigDecimal.ZERO) == 0) {
			throw new BusinessException("Para transferir é preciso ser uma quantia positiva");
		}

		if (valorContaAtual.compareTo(BigDecimal.ZERO) == -1 || valorContaAtual.compareTo(BigDecimal.ZERO) == 0
				|| (valorContaAtual.subtract(transferenciaDTO.valorTransferir()).compareTo(BigDecimal.ZERO) == -1)) {
			throw new BusinessException("Não foi possivel transferir pois não tem  saldo é suficente");
		}

		if (contaAtual.getTipoConta() == TipoConta.POUPANCA) {
			contaAtual.getContaPoupanca().setSaldo(
					valorContaAtual.subtract(transferenciaDTO.valorTransferir().subtract(new BigDecimal("2.00"))));
		} else {
			contaAtual.getContaCorrente().setSaldo(
					valorContaAtual.subtract(transferenciaDTO.valorTransferir().subtract(new BigDecimal("2.00"))));
		}

		if (contaDestino.getTipoConta() == TipoConta.POUPANCA) {
			BigDecimal valorContaDestino = contaDestino.getContaPoupanca().getSaldo();
			contaDestino.getContaPoupanca().setSaldo(valorContaDestino.add(transferenciaDTO.valorTransferir()));
		} else {
			BigDecimal valorContaDestino = contaDestino.getContaCorrente().getSaldo();
			contaDestino.getContaCorrente().setSaldo(valorContaDestino.add(transferenciaDTO.valorTransferir()));
		}

		contaRepository.save(contaAtual);
		contaRepository.save(contaDestino);

		return new ContaDto(contaAtual);
	}

	public BigDecimal obterSaldo(Long contaId) {

		Conta conta = contaRepository.findById(contaId)
				.orElseThrow(() -> new BusinessException("Não tem essa conta na base"));

		return conta.getTipoConta() == TipoConta.POUPANCA ? conta.getContaPoupanca().getSaldo()
				: conta.getContaCorrente().getSaldo();
	}

	public ContaDto salvaChavePix(Long contaId, SalvarChavePixDto salvarChavePixDto) {

		Conta conta = contaRepository.findById(contaId)
				.orElseThrow(() -> new BusinessException("Não tem essa conta na base"));

		if (conta.getChavePix() != null && !conta.getChavePix().isBlank()) {
			throw new BusinessException("Chave já cadastrada, para modificar deve atualizar");
		}

		rotinaSalvarOuAtualizarPix(conta, salvarChavePixDto);

		return new ContaDto(conta);
	}

	private Conta popularChavePix(SalvarChavePixDto salvarChavePixDto, Conta conta) {
		// TODO Auto-generated method stub
		if (salvarChavePixDto.tipoPix() != TipoPix.ALEATORIA) {
			conta.setChavePix(salvarChavePixDto.chavePix());

		} else {
			String chaveAleatoria = UUID.randomUUID().toString();
			conta.setChavePix(chaveAleatoria);
		}

		conta.setTipopix(salvarChavePixDto.tipoPix());

		return conta;

	}

	private void verificaValidacoesChavePix(SalvarChavePixDto salvarChavePixDto) {
		// TODO Auto-generated method stub
		if (salvarChavePixDto.chavePix().isBlank()) {
			throw new BusinessException("É necessário informar uma chave para os tipos: CPF, CNPJ EMAIL, CELULAR");
		}

		if (salvarChavePixDto.tipoPix() == TipoPix.CPF) {
			if (!salvarChavePixDto.chavePix().matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
				throw new BusinessException("Chave PIX inválida. Formato esperado: XXX.XXX.XXX-XX");
			}

			if (!Utils.isValidCPF(salvarChavePixDto.chavePix())) {
				throw new BusinessException("Este CPF não é valido");
			}

		}

		if (salvarChavePixDto.tipoPix() == TipoPix.CNPJ) {
			if (!salvarChavePixDto.chavePix().matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")) {
				throw new BusinessException("Chave PIX inválida. Formato esperado: XX.XXX.XXX/XXXX-XX");
			}
		}

		if (salvarChavePixDto.tipoPix() == TipoPix.CELULAR) {
			if (!salvarChavePixDto.chavePix().matches("\\(\\d{2}\\)\\s?9\\d{4}-\\d{4}")) {
				throw new BusinessException("Chave PIX inválida. Formato esperado: (DD) 9XXXX-XXXX");
			}
		}

		if (salvarChavePixDto.tipoPix() == TipoPix.EMAIL) {
			if (!salvarChavePixDto.chavePix().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
				throw new BusinessException("Formato de e-mail inválido para chave PIX");
			}
		}

	}

	private boolean verificaRegraSalvarPix(Conta conta, SalvarChavePixDto salvarChavePixDto) {
		// TODO Auto-generated method stub
		if (conta.getTipoConta() == TipoConta.POUPANCA) {
			throw new BusinessException("Contas do tipo poupança não pode ter chave pix");
		}

		if (conta.getChavePix() != null) {
			if (conta.getChavePix().equals(salvarChavePixDto.chavePix())) {
				throw new BusinessException("Essa chave ja esta  cadastrada para essa conta");
			}

			if (contaRepository.existsByChavePixAndContaNot(salvarChavePixDto.chavePix(), conta.getConta())) {
				throw new BusinessException("Essa chave ja esta  cadastrada para outra conta");
			}
		}

		return true;
	}

	public ContaDto atualizarChavePix(Long contaId, SalvarChavePixDto salvarChavePixDto) {

		Conta conta = contaRepository.findById(contaId)
				.orElseThrow(() -> new BusinessException("Não tem essa conta na base"));

		if (conta.getChavePix() == null || conta.getChavePix().isBlank()) {
			throw new BusinessException("É necessário salvar uma chave pix");
		}

		rotinaSalvarOuAtualizarPix(conta, salvarChavePixDto);

		return new ContaDto(conta);

	}

	private void rotinaSalvarOuAtualizarPix(Conta conta, SalvarChavePixDto salvarChavePixDto) {

		verificaRegraSalvarPix(conta, salvarChavePixDto);

		verificaValidacoesChavePix(salvarChavePixDto);

		contaRepository.save(popularChavePix(salvarChavePixDto, conta));

	}

	public void deletePixPorId(Long id, SalvarChavePixDto salvarChavePixDto) {

		contaRepository.findById(id).orElseThrow(() -> new BusinessException("Não tem essa conta na base"));

		Conta conta = contaRepository
				.findByChavePixAndTipopix(salvarChavePixDto.chavePix(), salvarChavePixDto.tipoPix())
				.orElseThrow(() -> new BusinessException("Não tem essa conta na base"));

		conta.setTipopix(null);
		conta.setChavePix(null);

		contaRepository.save(conta);
		// TODO Auto-generated method stub

	}

	public ContaDto transferenciaPix(Long contaId, TransferenciaDTO transferenciaDTO) {

		Conta contaAtual = contaRepository.findById(contaId)
				.orElseThrow(() -> new BusinessException("Não tem essa conta na base"));

		Conta contaDestino = contaRepository
				.findByChavePixAndTipopix(transferenciaDTO.pix().chavePix(), transferenciaDTO.pix().tipoPix())
				.orElseThrow(() -> new BusinessException("Não tem essa conta na base"));

		return movimentacaoTransferencia(contaAtual, contaDestino, transferenciaDTO);
	}

}
