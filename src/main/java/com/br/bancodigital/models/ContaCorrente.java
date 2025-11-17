package com.br.bancodigital.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaCorrente {
	
	@Id
	private Long id;
	
	@Column(name = "taxa_mensal", nullable = false, columnDefinition = "NUMERIC(10,2)")
	private BigDecimal taxaMensal;
	
	@OneToOne
	@MapsId
	@JoinColumn(name="conta_id", nullable = false, unique = true)
	private Conta conta;
	

}
