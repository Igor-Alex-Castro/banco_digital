package com.br.bancodigital.controollers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.bancodigital.dto.ApiResponse;
import com.br.bancodigital.dto.ContaDto;
import com.br.bancodigital.dto.TransferenciaDTO;
import com.br.bancodigital.models.Conta;
import com.br.bancodigital.services.ContaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/contas")
@Validated
public class ContaController {
	
	private final ContaService contaService;
	
	public ContaController(ContaService contaService) {
		this.contaService = contaService;
	}

	@PostMapping()
	public ResponseEntity<ApiResponse<ContaDto>> salvar(@RequestBody @Valid ContaDto contaDto){
		
		Conta contaCriada = contaService.salvar( contaDto);
		 
		
		 ApiResponse<ContaDto> response = new ApiResponse<>(
		 HttpStatus.CREATED.value(), "Cliente criado com sucesso", new
		 ContaDto(contaCriada) );
		 
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	@GetMapping("/{contaId}")
	public ResponseEntity<ApiResponse<ContaDto>> obterContasPorId(@PathVariable @NotNull(message = "O ID da conta é obrigatório") Long contaId){
		
		ContaDto contaDto = contaService.obterContasPorId(contaId);
		
		
		ApiResponse<ContaDto> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Conta carregada com sucesso",
				 	contaDto 
				 );
		return ResponseEntity.ok( response);
	}
	
	@GetMapping("/{id}/trasfarencia")
	public ResponseEntity<ApiResponse<TransferenciaDTO>> transferencia(
			@PathVariable @NotNull(message = "O ID da conta é obrigatório") Long contaId,
			@RequestBody TransferenciaDTO transferenciaDTO)
			{
		
				TransferenciaDTO transferenciaDTO = contaService.transferencia(contaId, transferenciaDTO);
		
		
		ApiResponse<TransferenciaDTO> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Conta carregada com sucesso",
				 	transferenciaDTO
				 );
		
		return ResponseEntity.ok( response);
	}
	
	
	
	@GetMapping("cliente/{idCliente}")
	public ResponseEntity<ApiResponse<List<ContaDto>>> obterContasPorIdCliente(@PathVariable @NotNull(message = "O ID do cliente é obrigatório") Long idCliente){
		
		List<ContaDto> contasDto = contaService.obterContasPorIdCliente(idCliente);
		
		
		ApiResponse<List<ContaDto>> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Contas do cliente carregado com sucesso",
				 	contasDto 
				 );
		return ResponseEntity.ok( response);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deletePorId(@PathVariable @NotNull(message = "O ID da conta é obrigatório") Long id){
		
	    contaService.deletePorId(id);
		
		ApiResponse<Void> response = new ApiResponse<Void>(
				 HttpStatus.NO_CONTENT.value(),
				 	"Cliente excluído com sucesso",
				 	null
				 );
		
	     return ResponseEntity.noContent().build();
	}
	

}
