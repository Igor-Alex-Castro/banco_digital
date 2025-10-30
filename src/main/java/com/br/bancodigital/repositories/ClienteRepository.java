package com.br.bancodigital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.bancodigital.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {


}
