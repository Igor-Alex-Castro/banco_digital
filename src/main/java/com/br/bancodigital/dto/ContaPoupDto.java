package com.br.bancodigital.dto;

import java.math.BigDecimal;

import com.br.bancodigital.models.Conta;


public record ContaPoupDto(
		Long id,
		
		BigDecimal taxaRendaAnual

		) implements ContaDetalheDto

{
	
	public ContaPoupDto(Conta conta) {
		this(
				
				conta.getContaPoupanca().getId(),
				conta.getContaPoupanca().getTaxaRendaAnual()
		);
	}
}
