package com.br.bancodigital.dto;

import java.math.BigDecimal;

import com.br.bancodigital.enuns.TipoCartao;
import com.br.bancodigital.models.Cartao;
import com.br.bancodigital.models.Conta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DetalhesCartaoDTO(
	
		@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Deve conter apenas letras e números, sem espaços")
		String agencia,
		
		@Pattern(regexp = "^[A-Za-z0-9]{6}$", message = "Deve conter apenas letras e números, sem espaços")
		String conta,
		
		boolean ativo,
		
		@NotNull(message = "O numero da conta é obrigatório")
		String numero,
		
		
		@NotNull(message = "O tipo da conta é obrigatório")
		TipoCartao tipoCartao,
		 
		
		
		BigDecimal limite,
		
		BigDecimal saldo
		
	
		
)

{

	public DetalhesCartaoDTO(Conta conta, Cartao cartao) {
		
		this(
				conta.getAgencia(),
				conta.getConta(),
				cartao.getAtivo(),
				cartao.getNumero(),
				cartao.getTipoCartao(),
				 cartao.getTipoCartao() == TipoCartao.DEBITO ? cartao.getCartaoDebito().getLimiteDiario()
					 : cartao.getCartaoCredito().getLimite(),
					 cartao.getTipoCartao() == TipoCartao.DEBITO ? conta.getSaldo() :
						 cartao.getCartaoCredito().getFatura()
					 
					 
				);
	}
}
