package com.br.bancodigital.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.br.bancodigital.enuns.TipoCartao;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class HistoricoPagamento {

	
	@Id
	@SequenceGenerator(
		name="hist_seq",
		sequenceName = "hist_sequence",
		allocationSize = 1
	)
	
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "hist_seq"
	)
	private Long id;
	
	private BigDecimal valor;
	
	
	private String descricao;
	

	private String numeroCod;
	
	private LocalDate dataPagamento;
	
	private Boolean paga;
	
	private String parcelaAtual;
	
	@Enumerated(EnumType.STRING) 
	private TipoCartao tipoCartao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conta_id", nullable = false)
	private Conta conta;
}
