package com.br.bancodigital.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.br.bancodigital.dto.CartaoDTO;
import com.br.bancodigital.dto.DetalhesCartaoDTO;
import com.br.bancodigital.dto.LimiteDto;
import com.br.bancodigital.dto.StatusDto;
import com.br.bancodigital.enuns.TipoCartao;
import com.br.bancodigital.exceptions.BusinessException;
import com.br.bancodigital.exceptions.ResourceNotFoundException;
import com.br.bancodigital.models.Cartao;
import com.br.bancodigital.models.CartaoCredito;
import com.br.bancodigital.models.CartaoDebito;
import com.br.bancodigital.models.Conta;
import com.br.bancodigital.repositories.CartaoCreditoRepository;
import com.br.bancodigital.repositories.CartaoDebitoRepository;
import com.br.bancodigital.repositories.CartaoRepository;
import com.br.bancodigital.repositories.ContaRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
public class CartaoService {

	private final ContaRepository contaRepository;
	private final CartaoRepository cartaoRepository;
	private final CartaoCreditoRepository cartaoCreditoRepository;
	private final CartaoDebitoRepository cartaoDebitoRepository;

	public CartaoService(ContaRepository contaRepository, CartaoRepository cartaoRepository,
			CartaoCreditoRepository cartaoCreditoRepository, CartaoDebitoRepository cartaoDebitoRepository) {
		this.contaRepository = contaRepository;
		this.cartaoRepository = cartaoRepository;
		this.cartaoCreditoRepository = cartaoCreditoRepository;
		this.cartaoDebitoRepository = cartaoDebitoRepository;
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

		return popularCartao(conta, cartaoDto);
	}

	private CartaoDTO popularCartao(Conta conta, CartaoDTO cartaoDto) {
		Cartao cartao = new Cartao();

		cartao.setAtivo(true);
		cartao.setNumero(cartaoDto.numero());
		cartao.setSenha(cartaoDto.senha());
		cartao.setTipoCartao(cartaoDto.tipoCartao());

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

		cartao.setCartaoCredito(cartaoCredito);

		conta.setCartao(cartao);

		contaRepository.save(conta);

		return cartao;
	}

	private Cartao popularCartaoDebito(Cartao cartao, Conta conta, CartaoDTO cartaoDto) {

		CartaoDebito cartaoDebito = new CartaoDebito();
		
		cartaoDebito.setLimiteDiario(cartaoDto.limite());

		cartao.setCartaoDebito(cartaoDebito);
		
		conta.setCartao(cartao);
		
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
				.orElseThrow(() -> new ResourceNotFoundException("Esta cartão não esta vinculado a nenhuma conta"));

		if (cartao.getTipoCartao() != TipoCartao.CREDITO) {
			throw new ResourceNotFoundException("Esta operação é somente para alterar o limite do cartao de credito");
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
			throw new ResourceNotFoundException("Esta operação é somente para alterar o limite do cartao de debito");
		}

		cartao.getCartaoDebito().setLimiteDiario(limiteDto.limite());

		cartaoRepository.save(cartao);

		return new DetalhesCartaoDTO(conta, cartao);
	}

	public DetalhesCartaoDTO status(Long id,
			 StatusDto statusDto) {
		
		Cartao cartao = cartaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Este cartão não existe"));

		Conta conta = contaRepository.findByCartao(cartao)
				.orElseThrow(() -> new ResourceNotFoundException("Esta cartão não esta vinculado a nenhuma conta"));

		if(statusDto.status() == true && cartao.getAtivo() == true) {
			throw new ResourceNotFoundException("Este cartão já esta ativo");
		}
		
		if(statusDto.status() == false && cartao.getAtivo() == false) {
			throw new ResourceNotFoundException("Este cartão já esta inativo");
		}
		
		cartao.setAtivo(statusDto.status());
		
		cartaoRepository.save(cartao);
		
		return new DetalhesCartaoDTO(conta, cartao);
	}
}
