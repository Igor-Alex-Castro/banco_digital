package com.br.bancodigital.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ClienteDto(

		@NotBlank(message = "o parâmetro 'nome' não pode ser vazio") String nome,

		@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
		message = "o CPF deve estar no formato XXX.XXX.XXX-XX"
		) 
		String cpf,

		@JsonFormat(pattern = "dd/MM/yyyy") 
		@NotNull(message = "O parâmetro 'dataNasce' é obrigatório e não pode ser nulo")
		LocalDate dataNasce,

		@NotBlank(message = "O parâmetro 'rua' é obrigatório e não pode ser nulo") 
		String rua,

		@NotBlank(message = "O parâmetro 'numero' é obrigatório e não pode ser nulo") 
		String numero,

		@NotBlank(message = "O parâmetro 'cidade' é obrigatório e não pode ser nulo") 
		String cidade,

		@NotBlank(message = "O parâmetro 'estado' é obrigatório e não pode ser nulo") 
		String estado

) {

}
