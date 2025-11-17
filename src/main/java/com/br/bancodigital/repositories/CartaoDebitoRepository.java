package com.br.bancodigital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.bancodigital.models.CartaoDebito;

@Repository
public interface CartaoDebitoRepository extends JpaRepository<CartaoDebito, Long> {

	

}
