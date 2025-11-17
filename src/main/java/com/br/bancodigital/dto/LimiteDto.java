package com.br.bancodigital.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record LimiteDto(
		@NotNull(message = "O parâmetro limite é o obrigatório")
		BigDecimal limite
		) {

}
