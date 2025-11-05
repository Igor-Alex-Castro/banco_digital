package com.br.bancodigital.models;

import java.math.BigDecimal;

import com.br.bancodigital.enuns.TipoConta;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	private Long id;
	
	@ManyToOne
	@JoinColumn(name="cliente_id", nullable = false)
	private Cliente cliente;
	
	@OneToOne(mappedBy = "conta", cascade = CascadeType.ALL)
	private ContaPonpanca contaPonpanca;
	
	@OneToOne(mappedBy = "conta", cascade = CascadeType.ALL)
	private ContaCorrente  contaCorrente;
	
	private String agencia;
	
	@Column( nullable = false, unique = true)
	private String conta;
	
	
	@Enumerated(EnumType.STRING) 
	@Column( nullable = false)
	private TipoConta tipoConta;
	
	
}
