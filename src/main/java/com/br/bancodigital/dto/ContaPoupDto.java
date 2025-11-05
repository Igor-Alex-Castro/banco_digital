package com.br.bancodigital.dto;

import java.math.BigDecimal;

import com.br.bancodigital.models.Conta;


public record ContaPoupDto(
		Long id,
		
		BigDecimal taxaRendaAnual,

		BigDecimal saldo
		) implements ContaDetalheDto

{
	
	public ContaPoupDto(Conta conta) {
		this(
				
				conta.getContaPonpanca().getId(),
				conta.getContaPonpanca().getTaxaRendaAnual(),
				conta.getContaPonpanca().getSaldo()
		);
	}
}
