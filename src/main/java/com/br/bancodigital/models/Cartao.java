package com.br.bancodigital.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.br.bancodigital.enuns.TipoCartao;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Cartao {
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column( nullable = false)
	private Boolean ativo;
	
	@Column( nullable = false, unique = true)
	private String numero;
	
	@Column( nullable = false, unique = true)
	private String senha;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="conta_id", nullable = false, unique = true)
	private Conta conta;
	
	 @OneToOne(mappedBy = "cartao", cascade = CascadeType.ALL, orphanRemoval = true)
	 private CartaoCredito cartaoCredito;
	 
	
	@OneToOne(mappedBy = "cartao", cascade = CascadeType.ALL, orphanRemoval = true)
	private CartaoDebito cartaoDebito;
	
	@Enumerated(EnumType.STRING) 
	private TipoCartao tipoCartao;
	
	@Column(name = "DATA_VIGENCIA", nullable = false)
	private LocalDate dataVigencia;
	
	@Column(name = "DATA_CRIACAO", nullable = false)
	private LocalDate dataCriacao;
	
	private LocalDate dataUltimoPagemento;
	
	
	@OneToMany( mappedBy = "cartao",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<CartaoSeguro> cartaoSeguro = new ArrayList<CartaoSeguro>();
}
