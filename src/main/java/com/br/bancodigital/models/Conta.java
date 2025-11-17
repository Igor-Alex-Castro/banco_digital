package com.br.bancodigital.models;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.br.bancodigital.enuns.TipoConta;
import com.br.bancodigital.enuns.TipoPix;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Conta {
	
	@Id
	@SequenceGenerator(
		name="conta_seq",
		sequenceName = "conta_sequence",
		allocationSize = 1
	)
	
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "conta_seq"
	)

	@EqualsAndHashCode.Include
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="cliente_id", nullable = false)
	private Cliente cliente;
	
	@OneToOne(mappedBy = "conta", cascade = CascadeType.ALL)
	private ContaPoupanca contaPoupanca;
	
	@OneToOne(mappedBy = "conta", cascade = CascadeType.ALL)
	private ContaCorrente  contaCorrente;
	
	private String agencia;
	
	 @EqualsAndHashCode.Include
  
	private String conta;
	
	 @Column(  unique = true)
	private String chavePix;
	
	@Enumerated(EnumType.STRING) 
	private TipoPix tipopix;
	
	@Enumerated(EnumType.STRING) 
	@Column( nullable = false)
	private TipoConta tipoConta;
	
	@Column(nullable = true, columnDefinition = "NUMERIC(10,2)")
	private BigDecimal saldo;
	
	
	@Column(name = "DATA_VIGENCIA", nullable = false)
	private LocalDate dataVigencia;
	
	@Column(name = "DATA_CRIACAO", nullable = false)
	private LocalDate dataCriacao;
	
	private LocalDate dataUltimoPagemento;
	
	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Manutencao> manutencoes;
	
	//@OneToOne(mappedBy = "conta", cascade = CascadeType.ALL,  orphanRemoval = true)
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "cartao_id", referencedColumnName = "id")
	private Cartao cartao;
}
