package com.br.bancodigital.dto;

import java.math.BigDecimal;

import com.br.bancodigital.enuns.TipoCartao;
import com.br.bancodigital.models.Cartao;
import com.br.bancodigital.models.Conta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CartaoSenhaDTO(
	
		
		
		@NotNull(message = "O numero da conta é obrigatório")
		String numero,
		
		@NotNull(message = "A senha da conta é obrigatório")
		String senha
		
		
		
)

{


}
