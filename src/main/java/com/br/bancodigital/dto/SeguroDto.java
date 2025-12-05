package com.br.bancodigital.dto;

import java.time.LocalDate;

import com.br.bancodigital.models.Cartao;
import com.br.bancodigital.models.CartaoSeguro;
import com.br.bancodigital.models.Seguro;

import jakarta.validation.constraints.NotNull;

public record SeguroDto(
		
		@NotNull(message = "O numero da conta é obrigatório")
		String numero,
		
		@NotNull(message = "A senha da conta é obrigatório")
		String senha,
		
		@NotNull(message = "O id do seguro é obrigatorio")
		Long idSeguro,
		
		
		 Integer numeroApolice,
		
		 LocalDate vigenciaFim,

		
	   LocalDate vigenciaIni
		
		
		
		
		) {
			/*
			 * public SeguroDto(Seguro seguro, Cartao cartao, CartaoSeguro cartaoSeguro) {
			 * this(seguro.getId(), cartao.getNumero() , cartao.getSenha(), seguro.getId(),
			 * cartaoSeguro.getApolice(), cartaoSeguro.getVigenciaFim(),
			 * cartaoSeguro.getVigenciaIni()); }
			 */
	public SeguroDto(Seguro seguro, Cartao cartao, CartaoSeguro cartaoSeguro) {
		// TODO Auto-generated constructor stub
		this(
				cartao.getNumero() ,
				cartao.getSenha(), 
				seguro.getId(),
				cartaoSeguro.getApolice(),
				cartaoSeguro.getVigenciaFim(),
				cartaoSeguro.getVigenciaIni());
	}
}
