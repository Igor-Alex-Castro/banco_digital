package com.br.bancodigital.dto;

import jakarta.validation.constraints.NotBlank;

public record SenhaDto(
		@NotBlank(message = "O parâmetro senha é obrigatório")
		String senha
		) {

}
