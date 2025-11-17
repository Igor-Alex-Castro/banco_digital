package com.br.bancodigital.models;

import java.time.LocalDate;
import java.util.List;

import com.br.bancodigital.enuns.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_CLIENTE") 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

	@Id
	@SequenceGenerator(
		name="cliente_seq",
		sequenceName = "cliente_sequence",
		allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "cliente_seq"
	)
	private Long id;
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Conta> contas;
	
	@Column(length = 100, nullable = false) 
	private String nome;
	
	@Column(length = 200, nullable = false) 
	private String cpf;
	
	@Column(name = "DATA_NASC", nullable = false)
	private LocalDate dataNasce;
	
	
	@Column( nullable = false)
	private String rua;
	
	
	private String numero;
	
	
	private String complemento;
	
	@Column( nullable = false)
	private String cidade;
	
	@Column( nullable = false)
	private String estado;

	@Column( nullable = false)
	private String cep;
	
	@Enumerated(EnumType.STRING) 
	@Column( nullable = false)
	private TipoCliente tipoCliente;
	
	

}
