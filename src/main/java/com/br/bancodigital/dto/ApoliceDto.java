package com.br.bancodigital.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.br.bancodigital.enuns.TipoSeguro;
import com.br.bancodigital.models.Cartao;
import com.br.bancodigital.models.CartaoSeguro;
import com.br.bancodigital.models.Seguro;

public record ApoliceDto(
	
	   Long idSeguro,
	   TipoSeguro tipoSeguro,	
	   BigDecimal valor,
	   String descricao,
	   Integer numeroApolice,
	   LocalDate vigenciaFim,
	   LocalDate vigenciaIni
		) {
			
			
	public ApoliceDto(Seguro seguro,  CartaoSeguro cartaoSeguro) {
		// TODO Auto-generated constructor stub
		this(seguro.getId(),
				seguro.getTipoSeguro(), 
				seguro.getValor(),
				seguro.getDescrição(),
				cartaoSeguro.getApolice(),
				cartaoSeguro.getVigenciaIni(),
				cartaoSeguro.getVigenciaFim()
				);
		
	}
}
