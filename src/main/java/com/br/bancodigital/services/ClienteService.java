package com.br.bancodigital.services;

import org.springframework.stereotype.Service;

import com.br.bancodigital.exceptions.BusinessException;
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
		
		
		Cliente clienteSalvo = null;
		if(clienteRepository.existsByCpf(cliente.getCpf())) {
			throw new  BusinessException("O Cliente com o cpf " +  cliente.getCpf() + " já existe" );
		}
		
		return  clienteRepository.save(cliente);
	}
}
