package com.br.bancodigital.dto;



import java.math.BigDecimal;

import com.br.bancodigital.enuns.TipoConta;
import com.br.bancodigital.enuns.TipoPix;
import com.br.bancodigital.models.Conta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ContaDto(
	
	@NotNull(message = "O id do cliente é obrigatório")
	Long clienteId,
    
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Deve conter apenas letras e números, sem espaços")
	@NotBlank(message = "A agencia deve ser obrigatória")
	String agencia,
    
	@Pattern(regexp = "^[A-Za-z0-9]{6}$", message = "Deve conter apenas letras e números, sem espaços")
	@NotBlank(message = "A conta deve ser obrigatória")
	String conta,
    
	@NotNull(message = "O tipo da conta deve ser obrigatória")
	TipoConta tipoConta,
	
	
	TipoPix tipopix,
	
	String chavePix,
    
	BigDecimal saldo,
	
	ContaDetalheDto info
    
) {
    public ContaDto(Conta conta) {
        this(
            conta.getCliente().getId(),
            conta.getAgencia(),
            conta.getConta(),
            conta.getTipoConta(),
            conta.getTipopix(),
            conta.getChavePix(),
            conta.getSaldo(),
            conta.getTipoConta() == TipoConta.POUPANCA  ? 
            	new ContaPoupDto(conta) : new ContaCorrenteDto(conta)
            	
            
            
        );
    }


}
