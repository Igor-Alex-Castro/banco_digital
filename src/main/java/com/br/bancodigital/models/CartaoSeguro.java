package com.br.bancodigital.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class CartaoSeguro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@ManyToOne
	@JoinColumn(name="cartao_id", nullable = false)
	private Cartao cartao;
	
	@ManyToOne
	@JoinColumn(name = "seguro_id", nullable = false)
	private Seguro seguro;
	
	private LocalDate vigenciaIni;
	
	private LocalDate vigenciaFim;
	


	
	private Integer apolice;

}
