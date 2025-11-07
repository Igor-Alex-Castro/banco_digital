package com.br.bancodigital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.bancodigital.models.ContaPoupanca;

public interface ContaPoupRepository extends JpaRepository<ContaPoupanca, Long> {

}
