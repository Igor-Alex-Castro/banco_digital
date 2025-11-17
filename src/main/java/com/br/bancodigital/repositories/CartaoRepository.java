package com.br.bancodigital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.bancodigital.models.Cartao;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long>  {

	boolean existsByNumero(String numero);
	
	boolean existsBySenha(String senha);
}
