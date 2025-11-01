package com.br.bancodigital.dto;

import java.time.LocalDate;

import com.br.bancodigital.models.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteDto(

		@NotBlank(message = "o parâmetro 'nome' não pode ser vazio")
		@Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
	    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "O nome deve conter apenas letras e espaços.")
		String nome,

		@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
		message = "O CPF deve estar no formato XXX.XXX.XXX-XX"
		) 
		String cpf,

		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") 
		@NotNull(message = "O parâmetro 'dataNasce' é obrigatório e não pode ser nulo")
		LocalDate dataNasce,

		@Pattern(
			    regexp = "\\d{5}-\\d{3}",
			    message = "O CEP deve estar no formato XXXXX-XXX"
			)
		@NotBlank(message = "O parâmetro 'cep' não pode ser vazio")
		String cep,
		

		@NotBlank(message = "O parâmetro 'numero' não pode ser vazio")
		String numero


) {
	
	public Cliente toEntity() {
        Cliente cliente = new Cliente();
        cliente.setNome(this.nome());
        cliente.setCpf(this.cpf());
        cliente.setDataNasce(this.dataNasce());  
        cliente.setNumero(this.numero());
  
        return cliente;
    }

}
