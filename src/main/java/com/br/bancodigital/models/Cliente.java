package com.br.bancodigital.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_CLIENTE") 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

	@Id
	@SequenceGenerator(
		name="cliente_seq",
		sequenceName = "cliente_sequence",
		allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "cliente_seq"
	)
	private Long id;
	
	@Column(length = 200, nullable = false) 
	private String nome;
	
	@Column(length = 200, nullable = false) 
	
	@Pattern(
		        regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
		        message = "O CPF deve estar no formato XXX.XXX.XXX-XX"
	)   
	private String cpf;
	
	@Column(name = "DATA_NASC", nullable = false)
	@JsonFormat(pattern = "dd/MM/yyyy")
	
	private LocalDate dataNasce;
	
	
	@Column( nullable = false)
	private String rua;
	
	@Column( nullable = false)
	private String numero;
	
	@Column( nullable = false)
	private String cidade;
	
	@Column( nullable = false)
	private String estado;

}
