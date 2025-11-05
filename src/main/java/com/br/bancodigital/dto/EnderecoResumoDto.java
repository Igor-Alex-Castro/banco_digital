package com.br.bancodigital.dto;

import com.br.bancodigital.models.Cliente;

public record EnderecoResumoDto(
		
		String rua,
		
		
		String cidade,
		
	
		String estado
		
		) {
	
	public EnderecoResumoDto(Cliente cliente) {
		this(
			cliente.getRua(),
			cliente.getCidade(),
			cliente.getEstado()
		);
		
		
	}

}
