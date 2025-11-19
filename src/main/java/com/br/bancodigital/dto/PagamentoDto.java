package com.br.bancodigital.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record PagamentoDto(
		@NotNull(message = "O numero da conta é obrigatório")
		String numero,
		
		@NotNull(message = "A senha da conta é obrigatório")
		String senha,
		
		@NotNull(message = "O valor do pagamento é obigatório")
		BigDecimal valor,
		
		@NotNull(message = "A descrição do pagamento é obigatório")
		String descricao,
		
		@NotNull(message = "O código de barras do pagamento é obigatório")
		String numeroCod,
		
		Integer numeroParcela
		) {

	
}
