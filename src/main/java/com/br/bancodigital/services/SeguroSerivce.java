package com.br.bancodigital.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.br.bancodigital.dto.ApoliceDto;
import com.br.bancodigital.dto.SeguroDto;
import com.br.bancodigital.enuns.TipoCliente;
import com.br.bancodigital.enuns.TipoSeguro;
import com.br.bancodigital.exceptions.BusinessException;
import com.br.bancodigital.models.Cartao;
import com.br.bancodigital.models.CartaoSeguro;
import com.br.bancodigital.models.Conta;
import com.br.bancodigital.models.Seguro;
//import com.br.bancodigital.repositories.ApoliceRepository;
import com.br.bancodigital.repositories.CartaoRepository;
import com.br.bancodigital.repositories.CartaoSeguroRepository;
import com.br.bancodigital.repositories.ContaRepository;
import com.br.bancodigital.repositories.SeguroRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@Service
public class SeguroSerivce {

	private final CartaoRepository cartaoRepository;
	private final SeguroRepository seguroRepository;
	private final ContaRepository contaRepository;
	private final CartaoSeguroRepository cartaoSeguroRepository;

	public SeguroSerivce(CartaoRepository cartaoRepository, ContaRepository contaRepository,
			SeguroRepository seguroRepository, CartaoSeguroRepository cartaoSeguroRepository) {
		// TODO Auto-generated constructor stub
		this.cartaoRepository = cartaoRepository;
		// this.apoliceRepository = apoliceRepository ;
		this.contaRepository = contaRepository;
		this.seguroRepository = seguroRepository;
		this.cartaoSeguroRepository = cartaoSeguroRepository;
	}

	@Transactional
	public SeguroDto salvar(SeguroDto seguroDto) {
		// TODO Auto-generated method stub

		Cartao cartao = cartaoRepository.findByNumeroAndSenha(seguroDto.numero(), seguroDto.senha())
				.orElseThrow(() -> new BusinessException("Esse cartão não está cadastrado"));

		Conta conta = contaRepository.findByCartao(cartao)
				.orElseThrow(() -> new BusinessException("Essa conta não está cadastrada"));

		if (cartaoSeguroRepository.existeSeguroVigencia(cartao.getId(), seguroDto.idSeguro())) {
			throw new BusinessException("Esse seguro já esta vigente para este cartao");
		};
		
		
		

		Seguro seguro = seguroRepository.findById(seguroDto.idSeguro())
				.orElseThrow(() -> new BusinessException("Seguro não encontrado"));

		if(conta.getCliente().getTipoCliente() == TipoCliente.PREMIUM) {
			if(seguro.getTipoSeguro() == TipoSeguro.VIAGEM_COMUM_SUPER ) {
				throw new BusinessException("Esse seguro não é habilitato para esse tipo de cliente");
			}
		}
		
		CartaoSeguro cartaoSeguro = new CartaoSeguro();
		
		
		
		

		cartaoSeguro.setVigenciaIni(LocalDate.now());
		cartaoSeguro.setVigenciaFim(LocalDate.now().plusMonths(12).withDayOfMonth(5));

		int apolice = ThreadLocalRandom.current().nextInt(10000000, 99999999);

		cartaoSeguro.setApolice(apolice);

		cartaoSeguro.setCartao(cartao);
		cartaoSeguro.setSeguro(seguro);

		cartao.getCartaoSeguro().add(cartaoSeguro);

		cartaoRepository.save(cartao);

		return new SeguroDto(seguro, cartao, cartaoSeguro);
	}

	public ApoliceDto obterApolice(Long idApolice) {
		// TODO Auto-generated method stub
		CartaoSeguro cartaoSeguro = cartaoSeguroRepository.findById(idApolice)
				.orElseThrow(() -> new BusinessException("Apolice não encontrada"));
		
		Seguro seguro = seguroRepository.findById(cartaoSeguro.getSeguro().getId())
				.orElseThrow(() -> new BusinessException("Seguro não encontrado"));
				
				return new ApoliceDto(seguro, cartaoSeguro);
	}

	public List<Seguro> obterSeguroDisponiveis() {
		// TODO Auto-generated method stub
		List<Seguro> seguro = seguroRepository.findAll();
		if( seguro.size() == 0) {
			throw new BusinessException("Nenhum seguro cadastrado");
		}
		return seguroRepository.findAll();
	}

}
