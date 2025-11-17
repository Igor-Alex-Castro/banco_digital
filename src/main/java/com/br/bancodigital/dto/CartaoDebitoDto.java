package com.br.bancodigital.dto;

import java.math.BigDecimal;

import com.br.bancodigital.models.Cartao;

public record CartaoDebitoDto  (
		
		Long id,
		
		BigDecimal limiteDiario
		
		) implements CartaoInfoDto {

	public CartaoDebitoDto(Cartao cartao) {
		this(cartao.getCartaoDebito().getId(), cartao.getCartaoDebito().getLimiteDiario());
	}
}
