package com.br.bancodigital.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.br.bancodigital.dto.ContaDto;
import com.br.bancodigital.dto.TransferenciaDTO;
import com.br.bancodigital.enuns.TipoCliente;
import com.br.bancodigital.enuns.TipoConta;
import com.br.bancodigital.exceptions.BusinessException;
import com.br.bancodigital.exceptions.ResourceNotFoundException;
import com.br.bancodigital.models.Cliente;
import com.br.bancodigital.models.Conta;
import com.br.bancodigital.models.ContaCorrente;
import com.br.bancodigital.models.ContaPonpanca;
import com.br.bancodigital.repositories.ClienteRepository;
import com.br.bancodigital.repositories.ContaCorrenteRepository;
import com.br.bancodigital.repositories.ContaPoupRepository;
import com.br.bancodigital.repositories.ContaRepository;

import jakarta.validation.constraints.NotNull;

@Service
public class ContaService {

	private final ContaRepository contaRepository;
	private final ClienteRepository clienteRepository;
	private final ContaPoupRepository contaPoupRepository;
	private final ContaCorrenteRepository contaCorrenteRepository;
	
	public ContaService(
			ContaRepository contaRepository, 
			ClienteRepository clienteRepository,
			ContaPoupRepository contaPoupRepository,
			ContaCorrenteRepository contaCorrenteRepository
			
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
		
		if(contaRepository.existsByConta(contaDto.conta())) {
			throw new  BusinessException("Esta conta já existe");
		};
		
		if(contaRepository.
				countByClienteIdAndTipoConta
				(cliente.getId(), contaDto.tipoConta()) >= 5) {
			 throw new BusinessException("Limite de 5 contas do tipo " + contaDto.tipoConta() + " para o número " + contaDto.conta() + " atingido.");
		}
		
		conta.setCliente(cliente);
		conta.setConta(contaDto.conta());
		conta.setAgencia(contaDto.agencia());
		conta.setTipoConta(contaDto.tipoConta());
		
		
		contaRepository.save(conta);
		
		if(contaDto.tipoConta() == TipoConta.POUPANCA) {
			ContaPonpanca contaPonpanca = new ContaPonpanca();
			contaPonpanca.setSaldo(new BigDecimal(0.00));
			
			if(cliente.getTipoCliente() == TipoCliente.COMUM) {
				
				contaPonpanca.setTaxaRendaAnual(new BigDecimal(0.5));
			}
			
			if(cliente.getTipoCliente() == TipoCliente.SUPER ) {
				contaPonpanca.setTaxaRendaAnual(new BigDecimal(0.7));
			}
			
			if(cliente.getTipoCliente() == TipoCliente.PREMIUM ) {
				contaPonpanca.setTaxaRendaAnual(new BigDecimal(0.9));
			}
			
			contaPonpanca.setConta(conta);
			conta.setContaPonpanca(contaPonpanca);
			
			contaPoupRepository.save(contaPonpanca);
		}else {
			ContaCorrente contaCorrente = new ContaCorrente();
			contaCorrente.setSaldo(new BigDecimal(0.00));
			
			if(cliente.getTipoCliente() == TipoCliente.COMUM) {
				
				contaCorrente.setTaxaMensal(new BigDecimal(12.00));
			}
			
			if(cliente.getTipoCliente() ==  TipoCliente.SUPER) {
				contaCorrente.setTaxaMensal(new BigDecimal(8.00));
			}
			
			if(cliente.getTipoCliente() ==  TipoCliente.PREMIUM ) {
				contaCorrente.setTaxaMensal(new BigDecimal(0.00));
			}
			contaCorrente.setConta(conta);
			conta.setContaCorrente(contaCorrente);
			
			contaCorrenteRepository.save(contaCorrente);
		}
		
		
		return conta;
	}

	public List<ContaDto> obterContasPorIdCliente(Long idCliente) {
		// TODO Auto-generated method stub
		List<Conta> contas = contaRepository.findByClienteId(idCliente);
		
		List<ContaDto> constasDto = contas.stream()
				.map(ContaDto::new)
				.toList();
		
		return constasDto;
	}

	public ContaDto obterContasPorId(Long contaId) {
		// TODO Auto-generated method stub
		Conta conta = contaRepository.findById(contaId)
				.orElseThrow(() ->  new BusinessException("Não tem essa conta na base"));
		
		
		return new ContaDto(conta);
	}

	public void deletePorId( Long id) {
		// TODO Auto-generated method stub
		contaRepository.findById(id)
				.orElseThrow(() ->  new BusinessException("Não tem essa conta na base"));
		
		contaRepository.deleteById(id);
	}

	public TransferenciaDTO transferencia( Long contaId,
			TransferenciaDTO transferenciaDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
