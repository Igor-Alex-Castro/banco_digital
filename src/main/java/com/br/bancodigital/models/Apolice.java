
package com.br.bancodigital.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Data

@Getter

@Setter

@AllArgsConstructor

@NoArgsConstructor
public class Apolice {

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String numeroApolice;

	@Column(nullable = false)
	private LocalDate vigenciaFim;

	@Column(nullable = false)
	private LocalDate vigenciaIni;
}
