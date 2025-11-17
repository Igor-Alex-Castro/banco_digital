package com.br.bancodigital.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.br.bancodigital.enuns.TipoCliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartaoDebito {
	
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@Column(nullable = true, columnDefinition = "NUMERIC(10,2)")
		private  BigDecimal limiteDiario;
		

}
