package com.br.bancodigital.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaPonpanca {
	@Id
	@SequenceGenerator(
		name="conta_poup_seq",
		sequenceName = "conta_poup_sequence",
		allocationSize = 1
	)
	
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "conta_poup_seq"
	)
	private Long id;
	
	@Column(name = "taxa_rend_anual", nullable = false, columnDefinition = "NUMERIC(10,2)")
	private BigDecimal taxaRendaAnual;
	
	@Column(nullable = true, columnDefinition = "NUMERIC(10,2)")
	private BigDecimal saldo;
	
	
	@OneToOne
	@JoinColumn(name="conta_id", nullable = false, unique = true)
	private Conta conta;

}
