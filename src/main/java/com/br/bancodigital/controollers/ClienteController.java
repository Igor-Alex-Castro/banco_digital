package com.br.bancodigital.controollers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.bancodigital.dto.ApiResponse;
import com.br.bancodigital.dto.ClienteDto;
import com.br.bancodigital.models.Cliente;
import com.br.bancodigital.services.ClienteService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

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
	public ResponseEntity<ApiResponse<ClienteDto>> salvar(@RequestBody @Valid ClienteDto clienteDto){
		
		Cliente clienteCriado = clienteService.salvarOuAtualiza(null, clienteDto);
		 
		ApiResponse<ClienteDto> response = new ApiResponse<>(
				 HttpStatus.CREATED.value(),
				 	"Cliente criado com sucesso",
				 	new ClienteDto(clienteCriado)
				 );
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ClienteDto>> atualizar(@PathVariable @NotNull(message = "O ID do cliente é obrigatório") Long id, @RequestBody @Valid ClienteDto clienteDto){
		
		Cliente clienteAtualizado = clienteService.salvarOuAtualiza(id, clienteDto);
		
		ApiResponse<ClienteDto> response = new ApiResponse<>(
				 HttpStatus.CREATED.value(),
				 	"Cliente atualizado com sucesso",
				 	new ClienteDto(clienteAtualizado)
				 );
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ClienteDto>> buscarPorId(@PathVariable @NotNull(message = "O ID do cliente é obrigatório") Long id){
		
		Cliente cliente = clienteService.buscarPorId(id);
		
		ApiResponse<ClienteDto> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Cliente carregado com sucesso",
				 	new ClienteDto(cliente)
				 );
		
	     return ResponseEntity.ok( response);
	}
	
	@GetMapping()
	public ResponseEntity<ApiResponse<List<ClienteDto>>> listarCliente(){
		
		List<ClienteDto> clientesDto= clienteService.listarCliente();
		
		ApiResponse<List<ClienteDto>> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Cliente carregado com sucesso",
				 	clientesDto
				 );
		
	     return ResponseEntity.ok( response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deletePorId(@PathVariable @NotNull(message = "O ID do cliente é obrigatório") Long id){
		
		clienteService.deletePorId(id);
		
		ApiResponse<Void> response = new ApiResponse<Void>(
				 HttpStatus.NO_CONTENT.value(),
				 	"Cliente excluído com sucesso",
				 	null
				 );
		
	     return ResponseEntity.noContent().build();
	}
}
