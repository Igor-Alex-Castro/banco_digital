package com.br.bancodigital.models;


import com.br.bancodigital.enuns.TipoConta;
import com.br.bancodigital.enuns.TipoPix;

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
	@Column( nullable = false, unique = true)
	private String conta;
	
	 @Column(  unique = true)
	private String chavePix;
	
	@Enumerated(EnumType.STRING) 
	private TipoPix tipopix;
	
	@Enumerated(EnumType.STRING) 
	@Column( nullable = false)
	private TipoConta tipoConta;
	
	
}
