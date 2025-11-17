package com.br.bancodigital.dto;

import java.math.BigDecimal;

import com.br.bancodigital.models.Cartao;

public record CartaoCreditoDto  (
		
		Long id,
		
		BigDecimal limite
		
		) implements CartaoInfoDto {

	public CartaoCreditoDto(Cartao cartao) {
		this(cartao.getCartaoCredito().getId(), cartao.getCartaoCredito().getLimite());
	}
}
