package com.br.bancodigital.dto;

import java.math.BigDecimal;

import com.br.bancodigital.enuns.TipoPix;
import com.br.bancodigital.validations.ValidaChavePix;

import jakarta.validation.Valid;


public record TransferenciaDTO(
		
		Long idDestino,
		
		String conta,
		
		String agencia,
		
		BigDecimal valorTransferir,
		
		@Valid
		SalvarChavePixDto pix
		
		) {
		
		
}
