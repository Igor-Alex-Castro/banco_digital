package com.br.bancodigital.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.br.bancodigital.enuns.TipoCliente;
import com.br.bancodigital.enuns.TipoSeguro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
public class Seguro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING) 
	@Column( nullable = false)
	private TipoSeguro tipoSeguro;
	
	@Column( nullable = false)
	private String descrição;
	
	@Column( nullable = false)
	private BigDecimal valor;
	
	
	
}
