package com.br.bancodigital.dto;

import java.math.BigDecimal;

import com.br.bancodigital.models.Conta;



public record ContaCorrenteDto(
		Long id,
		
		BigDecimal taxaMensal
		
		 
		) implements ContaDetalheDto {
	
	public ContaCorrenteDto(Conta conta) {
		this(
				
				conta.getContaCorrente().getId(),
				conta.getContaCorrente().getTaxaMensal()
		);
	}

}
