package com.br.bancodigital.dto;

import com.br.bancodigital.enuns.TipoPix;
import com.br.bancodigital.validations.ValidaChavePix;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ValidaChavePix
public record SalvarChavePixDto (
		
		TipoPix tipoPix,
		
		
		String chavePix
		
		
		) {

	

}
