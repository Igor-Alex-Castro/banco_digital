package com.br.bancodigital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.bancodigital.models.ContaCorrente;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Long> {

}
