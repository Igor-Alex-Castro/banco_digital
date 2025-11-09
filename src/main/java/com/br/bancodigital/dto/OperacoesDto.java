package com.br.bancodigital.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record OperacoesDto(
		@NotNull(message =  "O valor deve ser obrigatório")
		BigDecimal valor
		
		) {

}
