package com.br.bancodigital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.bancodigital.models.Conta;
import com.br.bancodigital.models.Manutencao;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
	
	boolean existsByContaAndMesReferenciaAndAnoReferencia(Conta conta, int mes, int ano);
	
}
