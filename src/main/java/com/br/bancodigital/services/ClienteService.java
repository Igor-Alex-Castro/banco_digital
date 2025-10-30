package com.br.bancodigital.services;

import org.springframework.stereotype.Service;

import com.br.bancodigital.models.Cliente;

import com.br.bancodigital.repositories.ClienteRepository;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;
	
	public ClienteService(ClienteRepository clienteRepository) {
		// TODO Auto-generated constructor stub
		this.clienteRepository = clienteRepository;
	}
	
	public Cliente salvar(Cliente cliente) {
		return  clienteRepository.save(cliente);
	}
}
