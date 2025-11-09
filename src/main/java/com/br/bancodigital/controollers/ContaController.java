package com.br.bancodigital.controollers;

import java.math.BigDecimal;
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
import com.br.bancodigital.dto.ContaDto;
import com.br.bancodigital.dto.OperacoesDto;
import com.br.bancodigital.dto.SalvarChavePixDto;
import com.br.bancodigital.dto.TransferenciaDTO;
import com.br.bancodigital.models.Cliente;
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
	
	@PostMapping("/{contaId}/trasfarencia")
	public ResponseEntity<ApiResponse<ContaDto>> transferencia(
			@PathVariable @NotNull(message = "O ID da conta é obrigatório") Long contaId,
			@RequestBody @Valid TransferenciaDTO transferenciaDTO)
			{
		
			ContaDto contaDto = contaService.transferencia(contaId, transferenciaDTO);
		
		
		ApiResponse<ContaDto> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Conta carregada com sucesso",
				 	contaDto
				 );
		
		return ResponseEntity.ok( response);
	}
	
	@PostMapping("/{contaId}/pix/trasfarencia")
	public ResponseEntity<ApiResponse<ContaDto>> transferenciaPix(
			@PathVariable @NotNull(message = "O ID da conta é obrigatório") Long contaId,
			@RequestBody @Valid TransferenciaDTO transferenciaDTO)
			{
		
			ContaDto contaDto = contaService.transferenciaPix(contaId, transferenciaDTO);
		
		
		ApiResponse<ContaDto> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Conta carregada com sucesso",
				 	contaDto
				 );
		
		return ResponseEntity.ok( response);
	}
	
	@PutMapping("/{id}/manutencao")
	public ResponseEntity<ApiResponse<ContaDto>> manutencao(@PathVariable @NotNull(message = "O ID da conta é obrigatório") long id){
		
		ContaDto clienteDto = contaService.manutencao(id);
		
		ApiResponse<ContaDto> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Cliente atualizado com sucesso",
				 	clienteDto
				 );
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/{contaId}/pix")
	public ResponseEntity<ApiResponse<ContaDto>> salvaChavePix(
			@PathVariable @NotNull(message = "O ID da conta é obrigatório") Long contaId,
			@RequestBody @Valid SalvarChavePixDto salvarChavePixDto)
			{
		
			ContaDto contaDto = contaService.salvaChavePix(contaId, salvarChavePixDto);
		
		
		ApiResponse<ContaDto> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Pix salvo com sucesso",
				 	contaDto
				 );
		
		return ResponseEntity.ok( response);
	}
	
	@PutMapping("/{contaId}/pix")
	public ResponseEntity<ApiResponse<ContaDto>> atualizarChavePix(
			@PathVariable @NotNull(message = "O ID da conta é obrigatório") Long contaId,
			@RequestBody @Valid SalvarChavePixDto salvarChavePixDto)
			{
		
			ContaDto contaDto = contaService.atualizarChavePix(contaId, salvarChavePixDto);
		
		
		ApiResponse<ContaDto> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Pix atualizado com sucesso",
				 	contaDto
				 );
		
		return ResponseEntity.ok( response);
	}
	
	
	@GetMapping("/{id}/saldo")
	public ResponseEntity<ApiResponse<BigDecimal>> obterSaldo(@PathVariable @NotNull(message = "O ID da conta é obrigatório") Long id){
		
		BigDecimal saldo = contaService.obterSaldo(id);
		
		
		ApiResponse<BigDecimal> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Saldo carregado com sucesso",
				 	saldo
				 );
		return ResponseEntity.ok( response);
	}
	
	@GetMapping("/cliente/{idCliente}")
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
	
	@DeleteMapping("/{id}/pix")
	public ResponseEntity<ApiResponse<Void>> deletePixPorChave(@PathVariable @NotNull(message = "O ID da conta é obrigatório") Long id,  
			@RequestBody @Valid SalvarChavePixDto salvarChavePixDto){
		
	    contaService.deletePixPorId(id, salvarChavePixDto);
		
		ApiResponse<Void> response = new ApiResponse<Void>(
				 HttpStatus.NO_CONTENT.value(),
				 	"Pix excluído com sucesso",
				 	null
				 );
		
	     return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/{id}/deposito")
	public ResponseEntity<ApiResponse<ContaDto>> depositoConta(
			@PathVariable @NotNull(message = "O ID da conta é obrigatório") Long id,
			@RequestBody @Valid OperacoesDto operacoesDto){
		
		ContaDto contasDto = contaService.depositar(id, operacoesDto);
		
		ApiResponse<ContaDto> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Deposito realizado com sucesso",
				 	contasDto 
				 );
		
		return ResponseEntity.ok( response);
	}
	
	@PostMapping("/{id}/saque")
	public ResponseEntity<ApiResponse<ContaDto>> saqueConta(
			@PathVariable @NotNull(message = "O ID da conta é obrigatório") Long id,
			@RequestBody @Valid OperacoesDto operacoesDto){
		
		ContaDto contasDto = contaService.sacar(id, operacoesDto);
		
		ApiResponse<ContaDto> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Deposito realizado com sucesso",
				 	contasDto 
				 );
		
		return ResponseEntity.ok( response);
	}

}
