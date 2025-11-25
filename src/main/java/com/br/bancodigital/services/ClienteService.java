package com.br.bancodigital.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.br.bancodigital.dto.ClienteDto;
import com.br.bancodigital.dto.EnderecoDto;
import com.br.bancodigital.exceptions.BusinessException;
import com.br.bancodigital.exceptions.ResourceNotFoundException;
import com.br.bancodigital.models.Cliente;
import com.br.bancodigital.repositories.ClienteRepository;
import com.br.bancodigital.utils.Utils;

import jakarta.validation.constraints.NotNull;

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
		
		
		validacoesCliente(null, clienteDto);

		Cliente cliente = popularCliente(clienteDto);
	
		
		return clienteRepository.save(cliente);
	}
	
	public Cliente atualiza(Long id, ClienteDto clienteDto) {
		
		validacoesCliente(id, clienteDto);

		Cliente cliente = popularCliente(clienteDto);
		
		
		buscarPorId(id);
		cliente.setId(id);
		
		
		return clienteRepository.save(cliente);
	}

	private Cliente popularCliente(ClienteDto clienteDto) {
		// TODO Auto-generated method stub
		
		EnderecoDto endereco = null;
		
		
		endereco = enderecoService.buscarEnderecoPorCep(clienteDto.cep());
			
		
		Cliente cliente = new Cliente();
		
		cliente.setNome(clienteDto.nome());
        cliente.setCpf(clienteDto.cpf());
        cliente.setDataNasce(clienteDto.dataNasce());
        cliente.setCep(endereco.cep());
        cliente.setRua(endereco.logradouro());
        cliente.setNumero(clienteDto.numero());
        cliente.setCidade(endereco.localidade());
        cliente.setEstado(endereco.estado());
        cliente.setComplemento(clienteDto.complemento());
        cliente.setTipoCliente(clienteDto.tipoCliente());
		 
		return cliente;
	}
	
	
	private  void validacoesCliente(Long id, ClienteDto clienteDto) {
		
		if(clienteRepository.existsByCpf(clienteDto.cpf()) && id == null) {
			throw new  BusinessException("O Cliente com o cpf " +  clienteDto.cpf() + " já existe" );
		}
		
		if(!Utils.isValidCPF(clienteDto.cpf())) {
			throw new  BusinessException("Este CPF não é valido");
		}
		
		if(!Utils.validaMaiorIdade(clienteDto.dataNasce())) {
			throw new  BusinessException("O cliente deve ter pelo menos 18 anos");
		}
		
		
	}

	public Cliente buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Este cliente não existe"));
	}

	public Cliente deletePorId(@NotNull(message = "O ID do cliente é obrigatório") Long id) {
		// TODO Auto-generated method stub
		Cliente cliente = null;
		try {
			
			cliente = clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Este cliente não existe"));
			clienteRepository.deleteById(id);
			
		
		}catch (Exception e) {
			e.printStackTrace();
			throw new  BusinessException("Existe contas associadas a este cliente.");
		}
		
		return cliente;
		
	}

	public List<ClienteDto> listarCliente() {
		// TODO Auto-generated method stub
		List<Cliente> clientes = clienteRepository.findAll();
		
		List<ClienteDto> clientesDto = clientes.stream()
				.map(ClienteDto::new)
				.toList();
		
		return clientesDto ;
	}
	
}
