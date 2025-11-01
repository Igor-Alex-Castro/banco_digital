package com.br.bancodigital.services;


import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import com.br.bancodigital.dto.ClienteDto;
import com.br.bancodigital.dto.EnderecoDto;
import com.br.bancodigital.exceptions.BusinessException;
import com.br.bancodigital.models.Cliente;
import com.br.bancodigital.repositories.ClienteRepository;
import com.br.bancodigital.utils.Utils;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;
	private final EnderecoService enderecoService;
	
	public ClienteService(ClienteRepository clienteRepository, EnderecoService enderecoService) {
		// TODO Auto-generated constructor stub
		this.clienteRepository = clienteRepository;
		this.enderecoService = enderecoService;
	}
	
	public Cliente salvar(ClienteDto clienteDto) {
		
		
		Cliente cliente = new Cliente();
		if(clienteRepository.existsByCpf(clienteDto.cpf())) {
			throw new  BusinessException("O Cliente com o cpf " +  clienteDto.cpf() + " já existe" );
		}
		
		if(!Utils.isValidCPF(clienteDto.cpf())) {
			throw new  BusinessException("Este CPF não é valido");
		}
		
		if(!Utils.validaMaiorIdade(clienteDto.dataNasce())) {
			throw new  BusinessException("O cliente deve ter pelo menos 18 anos");
		}
		
		EnderecoDto endereco = null;
		
			
		endereco = enderecoService.buscarEnderecoPorCep(clienteDto.cep());
			
		
		
		
		cliente.setNome(clienteDto.nome());
        cliente.setCpf(clienteDto.cpf());
        cliente.setDataNasce(clienteDto.dataNasce());
        cliente.setCep(endereco.cep());
        cliente.setRua(endereco.logradouro());
        cliente.setNumero(clienteDto.numero());
        cliente.setCidade(endereco.localidade());
        cliente.setEstado(endereco.estado());
		 
		return  clienteRepository.save(cliente);
	}
	
	
	
}
