package com.br.bancodigital.models;

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
import jakarta.persistence.ManyToOne;
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

	 @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	 @JoinColumn(name = "credito_id", referencedColumnName = "id") private
	 CartaoCredito cartaoCredito;
	 
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "debito_id", referencedColumnName = "id")
	private CartaoDebito cartaoDebito;
	
	@Enumerated(EnumType.STRING) 
	private TipoCartao tipoCartao;
}
