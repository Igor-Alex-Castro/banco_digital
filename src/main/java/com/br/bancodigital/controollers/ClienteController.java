package com.br.bancodigital.controollers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.bancodigital.dto.ApiResponse;
import com.br.bancodigital.dto.ClienteDto;
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
	public ResponseEntity<ApiResponse<Cliente>> salvar(@RequestBody @Valid ClienteDto clienteDto){
		
		Cliente clieteCriado = clienteService.salvar(clienteDto);
		 
		ApiResponse<Cliente> response = new ApiResponse<>(
				 HttpStatus.CREATED.value(),
				 	"Cliente criado com sucesso",
				 	clieteCriado
				 );
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
