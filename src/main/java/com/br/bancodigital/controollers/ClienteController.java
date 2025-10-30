package com.br.bancodigital.controollers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.bancodigital.models.Cliente;
import com.br.bancodigital.services.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
@Validated
public class ClienteController {

	private final ClienteService clienteService;
	
	public ClienteController(ClienteService clienteService) {
		this.clienteService =  clienteService;
		// TODO Auto-generated constructor stub
		
	}
	
	@PostMapping()
	public ResponseEntity<Cliente> salvar(@RequestBody @Valid Cliente cliente){
		
		clienteService.salvar(cliente);
		
		return null;
	}
}
