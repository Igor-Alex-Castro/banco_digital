package com.br.bancodigital.models;

import java.math.BigDecimal;
import java.time.LocalDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Manutencao {


	@Id
	@SequenceGenerator(
		name="manutecao_seq",
		sequenceName = "manu_sequence",
		allocationSize = 1
	)
	
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "conta_seq"
	)

	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conta_id", nullable = false)
	private Conta conta;
	
	@Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorCobrado;

    @Column(nullable = false, length = 150)
    private String descricao;

    @Column(nullable = false)
    private int mesReferencia;

    @Column(nullable = false)
    private int anoReferencia;
	
	
}
