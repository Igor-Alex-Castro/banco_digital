package com.br.bancodigital.models;

import java.math.BigDecimal;

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
public class ContaPoupanca {
	@Id
	private Long id;
	
	@Column(name = "taxa_rend_anual", nullable = false, columnDefinition = "NUMERIC(10,2)")
	BigDecimal taxaRendaAnual;
	
	@Column(nullable = true, columnDefinition = "NUMERIC(10,2)")
	private BigDecimal saldo;
	
	
	@OneToOne
	@MapsId
	@JoinColumn(name="conta_id", nullable = false, unique = true)
	private Conta conta;

}
