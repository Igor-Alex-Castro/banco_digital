package com.br.bancodigital.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.bancodigital.dto.CartaoDTO;
import com.br.bancodigital.dto.DetalhesCartaoDTO;
import com.br.bancodigital.dto.FaturaDto;
import com.br.bancodigital.dto.LimiteDto;
import com.br.bancodigital.dto.PagamentoDto;
import com.br.bancodigital.dto.SenhaDto;
import com.br.bancodigital.dto.StatusDto;
import com.br.bancodigital.enuns.TipoCartao;
import com.br.bancodigital.enuns.TipoConta;
import com.br.bancodigital.enuns.TipoPix;
import com.br.bancodigital.exceptions.BusinessException;
import com.br.bancodigital.exceptions.ResourceNotFoundException;
import com.br.bancodigital.models.Cartao;
import com.br.bancodigital.models.CartaoCredito;
import com.br.bancodigital.models.CartaoDebito;
import com.br.bancodigital.models.Cliente;
import com.br.bancodigital.models.Conta;
import com.br.bancodigital.models.ContaCorrente;
import com.br.bancodigital.models.ContaPoupanca;
import com.br.bancodigital.models.HistoricoPagamento;
import com.br.bancodigital.models.Manutencao;
import com.br.bancodigital.repositories.CartaoRepository;
import com.br.bancodigital.repositories.ContaRepository;
import com.br.bancodigital.repositories.HistoricoPagamentoRepository;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Service
public class CartaoService {

	private final ContaRepository contaRepository;
	private final CartaoRepository cartaoRepository;

	private final HistoricoPagamentoRepository historicoPagamentoRepository;

	public CartaoService(ContaRepository contaRepository, CartaoRepository cartaoRepository,
			HistoricoPagamentoRepository historicoPagamentoRepository) {
		this.contaRepository = contaRepository;
		this.cartaoRepository = cartaoRepository;
		this.historicoPagamentoRepository = historicoPagamentoRepository;

	}

	public CartaoDTO salvar(CartaoDTO cartaoDto) {
		// TODO Auto-generated method stub
		Conta conta = contaRepository.findByContaAndAgencia(cartaoDto.conta(), cartaoDto.agencia())
				.orElseThrow(() -> new ResourceNotFoundException("Esta conta não existe"));

		if (conta.getCartao() != null) {
			throw new BusinessException(
					"Esta conta já possui um cartão. Para modificar algo, atualize o cartão existente");
		}

		if (cartaoRepository.existsByNumero(cartaoDto.numero())) {
			throw new BusinessException("Já existe um cartão com este número");
		}

		if (cartaoRepository.existsBySenha(cartaoDto.senha())) {
			throw new BusinessException("Já existe um cartão com esta senha");
		}

		if (cartaoDto.tipoCartao() == TipoCartao.CREDITO && conta.getTipoConta() == TipoConta.POUPANCA) {
			throw new BusinessException("Contas tipos poupança não pode ter cartão de credito");
		}

		return popularCartao(conta, cartaoDto);
	}

	private CartaoDTO popularCartao(Conta conta, CartaoDTO cartaoDto) {
		Cartao cartao = new Cartao();

		cartao.setAtivo(true);
		cartao.setNumero(cartaoDto.numero());
		cartao.setSenha(cartaoDto.senha());
		cartao.setTipoCartao(cartaoDto.tipoCartao());
		cartao.setDataCriacao(LocalDate.now());

		LocalDate proximoDia5;

		if (LocalDate.now().getDayOfMonth() > 5) {
			proximoDia5 = LocalDate.now().plusMonths(1).withDayOfMonth(5);
		} else {
			proximoDia5 = LocalDate.now().withDayOfMonth(5);
		}

		cartao.setDataVigencia(proximoDia5);

		if (cartaoDto.tipoCartao() == TipoCartao.DEBITO) {

			// cartaoDebitoRepository.save(cartaoDebito);
			popularCartaoDebito(cartao, conta, cartaoDto);
		} else {

			popularCartaoCredito(cartao, conta, cartaoDto);
		}

		return new CartaoDTO(conta, cartao);

	}

	private Cartao popularCartaoCredito(Cartao cartao, Conta conta, CartaoDTO cartaoDto) {
		CartaoCredito cartaoCredito = new CartaoCredito();
		cartaoCredito.setLimite(cartaoDto.limite());
		cartaoCredito.setFatura(cartaoDto.saldo());

		cartaoCredito.setCartao(cartao);
		
		cartao.setCartaoCredito(cartaoCredito);

		cartao.setConta(conta);
		
		conta.setCartao(cartao);

		cartaoRepository.save(cartao);
		contaRepository.save(conta);

		return cartao;
	}

	private Cartao popularCartaoDebito(Cartao cartao, Conta conta, CartaoDTO cartaoDto) {

		CartaoDebito cartaoDebito = new CartaoDebito();

		cartaoDebito.setLimiteDiario(cartaoDto.limite());

		cartaoDebito.setCartao(cartao);
		
		
		cartao.setCartaoDebito(cartaoDebito);
		///cartaoRepository.save(cartao);

		cartao.setConta(conta);

		conta.setCartao(cartao);
		//cartao.setConta(conta);

		
		cartaoRepository.save(cartao);
		contaRepository.save(conta);
		
		return cartao;

	}

	public DetalhesCartaoDTO obterCartao(Long id) {
		// TODO Auto-generated method stub
		Cartao cartao = cartaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Este cartão não existe"));

		Conta conta = contaRepository.findByCartao(cartao)
				.orElseThrow(() -> new ResourceNotFoundException("Esta cartão não esta vinculado a nenhuma conta"));
		;

		return new DetalhesCartaoDTO(conta, cartao);
	}

	public DetalhesCartaoDTO limiteCredito(Long id, LimiteDto limiteDto) {

		Cartao cartao = cartaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Este cartão não existe"));

		Conta conta = contaRepository.findByCartao(cartao)
				.orElseThrow(() -> new ResourceNotFoundException("Este cartão não esta vinculado a nenhuma conta"));

		if (cartao.getTipoCartao() != TipoCartao.CREDITO) {
			throw new BusinessException("Esta operação é somente para alterar o limite do cartao de credito");
		}

		cartao.getCartaoCredito().setLimite(limiteDto.limite());

		cartaoRepository.save(cartao);

		return new DetalhesCartaoDTO(conta, cartao);
	}

	public DetalhesCartaoDTO limiteDebito(Long id, LimiteDto limiteDto) {

		Cartao cartao = cartaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Este cartão não existe"));

		Conta conta = contaRepository.findByCartao(cartao)
				.orElseThrow(() -> new ResourceNotFoundException("Esta cartão não esta vinculado a nenhuma conta"));

		if (cartao.getTipoCartao() != TipoCartao.DEBITO) {
			throw new BusinessException("Esta operação é somente para alterar o limite do cartao de debito");
		}

		cartao.getCartaoDebito().setLimiteDiario(limiteDto.limite());

		cartaoRepository.save(cartao);

		return new DetalhesCartaoDTO(conta, cartao);
	}

	public DetalhesCartaoDTO status(Long id, StatusDto statusDto) {

		Cartao cartao = cartaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Este cartão não existe"));

		Conta conta = contaRepository.findByCartao(cartao)
				.orElseThrow(() -> new ResourceNotFoundException("Esta cartão não esta vinculado a nenhuma conta"));

		if (statusDto.status() == true && cartao.getAtivo() == true) {
			throw new BusinessException("Este cartão já esta ativo");
		}

		if (statusDto.status() == false && cartao.getAtivo() == false) {
			throw new BusinessException("Este cartão já esta inativo");
		}

		cartao.setAtivo(statusDto.status());

		cartaoRepository.save(cartao);

		return new DetalhesCartaoDTO(conta, cartao);
	}

	public DetalhesCartaoDTO status(Long id, SenhaDto senhaDto) {

		Cartao cartao = cartaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Este cartão não existe"));

		Conta conta = contaRepository.findByCartao(cartao)
				.orElseThrow(() -> new ResourceNotFoundException("Esta cartão não esta vinculado a nenhuma conta"));

		if (cartao.getSenha().equals(senhaDto.senha())) {
			throw new BusinessException("A nova senha deve ser diferente da antiga");
		}
		;

		cartao.setSenha(senhaDto.senha());

		cartaoRepository.save(cartao);

		return new DetalhesCartaoDTO(conta, cartao);
	}

	public FaturaDto fatura(Long id, YearMonth mes) {
		// TODO Auto-generated method stub

		LocalDate dataInicio = LocalDate.of(mes.getYear(), mes.getMonthValue(), 5);

		LocalDate dataFim = dataInicio.plusMonths(1).withDayOfMonth(4);

		Cartao cartao = cartaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Este cartão não existe"));

		contaRepository.findByCartao(cartao)
				.orElseThrow(() -> new ResourceNotFoundException("Esta cartão não esta vinculado a nenhuma conta"));

		BigDecimal fatura = historicoPagamentoRepository.somarPorCartao(id, dataInicio, dataFim)
				.orElse(BigDecimal.ZERO);

		if (cartao.getTipoCartao() != TipoCartao.CREDITO) {
			throw new BusinessException("Somente cartões do tipo Credito tem fatura");
		}

		return new FaturaDto(fatura);
	}

	public DetalhesCartaoDTO pagemento(Long id, PagamentoDto pagamentoDto) {

		Cartao cartao = cartaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Este cartão não existe"));

		Conta conta = contaRepository.findByCartao(cartao)
				.orElseThrow(() -> new ResourceNotFoundException("Esta cartão não esta vinculado a nenhuma conta"));

		if (historicoPagamentoRepository.existsByNumeroCod(pagamentoDto.numeroCod())) {
			throw new BusinessException("Esta conta foi paga ou esta em pedencia, aguarde o fechamento da fatura");
		}

		if (cartao.getTipoCartao() == TipoCartao.CREDITO) {
			pagaContaCartaoCredito(conta, cartao, pagamentoDto);
		} else {

			pagaContaCartaoDebito(conta, cartao, pagamentoDto);
		}
		return new DetalhesCartaoDTO(conta, cartao);
	}

	private void pagaContaCartaoDebito(Conta conta, Cartao cartao, PagamentoDto pagamentoDto) {
		BigDecimal limiteDiario = cartao.getCartaoDebito().getLimiteDiario();

		BigDecimal saldoAtual = conta.getSaldo() == null ? BigDecimal.ZERO : conta.getSaldo();

		BigDecimal saldoSubatractPagamento = saldoAtual.subtract(pagamentoDto.valor());

		BigDecimal pagamentosHoje = historicoPagamentoRepository
				.limiteDiarioPorCartaoDebito(cartao.getId(), LocalDate.now()).orElse(BigDecimal.ZERO);

		if (limiteDiario.compareTo(pagamentosHoje.add(pagamentoDto.valor())) == -1) {
			throw new BusinessException("Vocâ atingiu o seu limite diario");
		}

		if (saldoSubatractPagamento.compareTo(BigDecimal.ZERO) == -1) {
			throw new BusinessException("Saldo insuficiente");
		}

		HistoricoPagamento historicoPagamento = new HistoricoPagamento();
		historicoPagamento.setConta(conta);
		historicoPagamento.setValor(pagamentoDto.valor());
		historicoPagamento.setDescricao(pagamentoDto.descricao());
		historicoPagamento.setNumeroCod(pagamentoDto.numeroCod());
		historicoPagamento.setDataPagamento(LocalDate.now());
		historicoPagamento.setPaga(true);
		historicoPagamento.setParcelaAtual("NENHUMA");
		historicoPagamento.setTipoCartao(TipoCartao.DEBITO);

		historicoPagamentoRepository.save(historicoPagamento);

		conta.setSaldo(saldoSubatractPagamento);

		contaRepository.save(conta);
	}

	private void pagaContaCartaoCredito(Conta conta, Cartao cartao, PagamentoDto pagamentoDto) {
		BigDecimal faturaAtualizada = verificaUltrapassouLimiteMes(cartao, pagamentoDto.valor());

		if (pagamentoDto.numeroParcela() == null || pagamentoDto.numeroParcela() == 0) {
			pagarSemParcela(conta, pagamentoDto);

		} else {

			pagarComParcela(conta, pagamentoDto);
		}

		cartao.getCartaoCredito().setFatura(faturaAtualizada);

		cartaoRepository.save(cartao);

	}

	private void pagarComParcela(Conta conta, PagamentoDto pagamentoDto) {
		BigDecimal valorParcela = pagamentoDto.valor().divide(BigDecimal.valueOf(pagamentoDto.numeroParcela()), 2,
				RoundingMode.HALF_UP);

		for (int i = 1; i <= pagamentoDto.numeroParcela(); i++) {
			HistoricoPagamento historicoPagamento = new HistoricoPagamento();
			historicoPagamento.setConta(conta);
			historicoPagamento.setValor(valorParcela);
			historicoPagamento.setDescricao(pagamentoDto.descricao());
			historicoPagamento.setNumeroCod(pagamentoDto.numeroCod());
			historicoPagamento.setDataPagamento(LocalDate.now().plusMonths(i).withDayOfMonth(5));
			historicoPagamento.setPaga(false);
			historicoPagamento.setParcelaAtual(i + "/" + pagamentoDto.numeroParcela().toString());
			historicoPagamento.setTipoCartao(TipoCartao.CREDITO);
			historicoPagamentoRepository.save(historicoPagamento);
		}

	}

	private void pagarSemParcela(Conta conta, PagamentoDto pagamentoDto) {
		HistoricoPagamento historicoPagamento = new HistoricoPagamento();
		historicoPagamento.setConta(conta);
		historicoPagamento.setValor(pagamentoDto.valor());
		historicoPagamento.setDescricao(pagamentoDto.descricao());
		historicoPagamento.setNumeroCod(pagamentoDto.numeroCod());
		historicoPagamento.setDataPagamento(LocalDate.now().plusMonths(1).withDayOfMonth(5));
		historicoPagamento.setPaga(false);
		historicoPagamento.setParcelaAtual("NENHUMA");
		historicoPagamento.setTipoCartao(TipoCartao.CREDITO);

		historicoPagamentoRepository.save(historicoPagamento);

	}

	private BigDecimal verificaUltrapassouLimiteMes(Cartao cartao, BigDecimal valor) {
		// TODO Auto-generated method stub
		// LocalDate dataFim = LocalDate.now().plusMonths(1).withDayOfMonth(5);
		// LocalDate dataInicio = LocalDate.now().withDayOfMonth(5);

		BigDecimal valorFatura = historicoPagamentoRepository.faturaPorCartaoNaoPaga(cartao.getId(), false);

		valorFatura = valorFatura == null ? valor : valorFatura.add(valor);

		BigDecimal limite = cartao.getCartaoCredito().getLimite();

		if (limite.compareTo(valorFatura) == -1) {
			throw new BusinessException("Vocâ atingiu o seu limite");
		}

		return valorFatura;

	}

	public DetalhesCartaoDTO pagementoFaturaCredito(Long id) {
		Cartao cartao = cartaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Este cartão não existe"));

		Conta conta = contaRepository.findByCartao(cartao)
				.orElseThrow(() -> new ResourceNotFoundException("Esta cartão não esta vinculado a nenhuma conta"));

		if (cartao.getTipoCartao() != TipoCartao.CREDITO) {
			new BusinessException("Somente cartão de creditos podem realizar esta ação");
		}

		LocalDate hoje = LocalDate.of(2025, 12, 5);

		if (hoje.isBefore(cartao.getDataVigencia())) {
			throw new BusinessException("Cobrança não aplicada: ainda não atingiu a data de vigência.");
		}

		LocalDate dataUltimoPagamento = cartao.getDataUltimoPagemento();

		LocalDate inicioCobranca = dataUltimoPagamento != null ? dataUltimoPagamento.plusMonths(1)
				: conta.getDataVigencia();

		inicioCobranca = inicioCobranca.withDayOfMonth(5);

		LocalDate fimCobranca = hoje.getDayOfMonth() < 5 ? hoje.withDayOfMonth(5).minusMonths(1)
				: hoje.withDayOfMonth(5);

		if (inicioCobranca.isAfter(fimCobranca)) {
			throw new BusinessException("Nenhum mês pendente de cobrança.");
		}

		List<HistoricoPagamento> listaContasPagar = historicoPagamentoRepository
				.listaPagaentoCredito(TipoCartao.CREDITO, cartao.getId(), inicioCobranca, fimCobranca);

		if (listaContasPagar.size() == 0) {
			throw new BusinessException("A fatura deste mês já foi paga.");
		}

		BigDecimal saldoAtual = conta.getSaldo() != null ? conta.getSaldo() : BigDecimal.ZERO;
		BigDecimal fatura = conta.getCartao().getCartaoCredito().getFatura();

		if (fatura == null || fatura.compareTo(BigDecimal.ZERO) == -1 || fatura.compareTo(BigDecimal.ZERO) == 0) {
			throw new BusinessException("Sem fatura");
		}

		BigDecimal valorFaturaPaga = BigDecimal.ZERO;

		for (HistoricoPagamento historicoPagamento : listaContasPagar) {
			saldoAtual = saldoAtual.subtract(historicoPagamento.getValor());

			valorFaturaPaga = valorFaturaPaga.add(historicoPagamento.getValor());

			historicoPagamento.setPaga(true);

			historicoPagamentoRepository.save(historicoPagamento);
		}

		fatura = fatura.subtract(valorFaturaPaga);
		cartao.getCartaoCredito().setFatura(fatura);

		conta.setDataUltimoPagemento(fimCobranca);

		cartaoRepository.save(cartao);
		contaRepository.save(conta);

		return new DetalhesCartaoDTO(conta, cartao);
	}

	public void deletePorId(Long id) {
		// TODO Auto-generated method stub
		cartaoRepository.findById(id).orElseThrow(() -> new BusinessException("Não tem essa cartao na base"));

		cartaoRepository.deleteById(id);
	}
}
