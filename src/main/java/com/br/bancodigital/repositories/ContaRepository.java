package com.br.bancodigital.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.bancodigital.enuns.TipoConta;
import com.br.bancodigital.models.Conta;

import jakarta.validation.constraints.NotNull;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

	boolean existsByConta(String conta);
	
	long countByContaAndTipoConta(String conta, TipoConta tipoConta);

	List<Conta> findByClienteId(Long idCliente);

	long countByClienteIdAndTipoConta(Long id, TipoConta tipoConta);

}
