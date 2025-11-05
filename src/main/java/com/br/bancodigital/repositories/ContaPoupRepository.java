package com.br.bancodigital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.bancodigital.models.ContaPonpanca;

public interface ContaPoupRepository extends JpaRepository<ContaPonpanca, Long> {

}
