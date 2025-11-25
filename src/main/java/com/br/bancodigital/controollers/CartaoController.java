package com.br.bancodigital.controollers;

import java.time.YearMonth;

import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.bancodigital.dto.ApiResponse;
import com.br.bancodigital.dto.CartaoDTO;
import com.br.bancodigital.dto.DetalhesCartaoDTO;
import com.br.bancodigital.dto.FaturaDto;
import com.br.bancodigital.dto.LimiteDto;
import com.br.bancodigital.dto.PagamentoDto;
import com.br.bancodigital.dto.StatusDto;
import com.br.bancodigital.services.CartaoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RequestMapping("/cartoes")
@Validated
@RestController
public class CartaoController {

	private final CartaoService cartaoService;
	
	public CartaoController (CartaoService cartaoService) {
		// TODO Auto-generated constructor stub
		this.cartaoService = cartaoService;
	}
	
	@PostMapping()
	public ResponseEntity<ApiResponse<CartaoDTO>> salvar(@RequestBody @Valid CartaoDTO cartaoDto){
		
		CartaoDTO cartaoDTO = cartaoService.salvar(cartaoDto);
		 
		ApiResponse<CartaoDTO> response = new ApiResponse<>(
				 HttpStatus.CREATED.value(),
				 	"Cartão criado com sucesso",
				 	cartaoDTO
				 );
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<DetalhesCartaoDTO>> obterCartao(@PathVariable @NotNull(message = "O ID da é obrigatório") Long id){
		
		DetalhesCartaoDTO detalhesCartaoDto =  cartaoService.obterCartao(id);
		
		ApiResponse<DetalhesCartaoDTO> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Cartão carregado com sucesso",
				 	detalhesCartaoDto
				 );
		
	     return ResponseEntity.ok( response);
	}
	
	@PutMapping("/{id}/limite")
	public ResponseEntity<ApiResponse<DetalhesCartaoDTO>> limiteCredito(@PathVariable @NotNull(message = "O ID do cliente é obrigatório") Long id, @RequestBody @Valid LimiteDto limiteDto){
		
		DetalhesCartaoDTO detalhesCartaoDTO = cartaoService.limiteCredito(id, limiteDto);
		
		ApiResponse<DetalhesCartaoDTO> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Limite do cartão atualizado com sucesso",
				 	detalhesCartaoDTO
				 );
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PutMapping("/{id}/limite-diario")
	public ResponseEntity<ApiResponse<DetalhesCartaoDTO>> limiteDebito(@PathVariable @NotNull(message = "O ID do cliente é obrigatório") Long id, @RequestBody @Valid LimiteDto limiteDto){
		
		DetalhesCartaoDTO detalhesCartaoDTO = cartaoService.limiteDebito(id, limiteDto);
		
		ApiResponse<DetalhesCartaoDTO> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Cliente atualizado com sucesso",
				 	detalhesCartaoDTO
				 );
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PutMapping("/{id}/status")
	public ResponseEntity<ApiResponse<DetalhesCartaoDTO>> status(@PathVariable @NotNull(message = "O ID do cartão é obrigatório") Long id, @RequestBody @Valid StatusDto statusDto){
		
		DetalhesCartaoDTO detalhesCartaoDTO = cartaoService.status(id, statusDto);
		
		ApiResponse<DetalhesCartaoDTO> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Status do cartao atualizado com sucesso",
				 	detalhesCartaoDTO
				 );
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/{id}/fatura")
	public ResponseEntity<ApiResponse<FaturaDto>> fatura(
			@PathVariable @NotNull(message = "O ID do cartão é obrigatório") Long id,
			@RequestParam  
			@DateTimeFormat(pattern = "MM/yyyy") 
			@NotNull(message = "O mês do cartão é obrigatório") YearMonth mes){
	
		FaturaDto faturaDto = cartaoService.fatura(id, mes);
		
		ApiResponse<FaturaDto> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Fatura carregada com sucesso",
				 	faturaDto
				 );
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/{id}/pagamento")
	public ResponseEntity<ApiResponse<DetalhesCartaoDTO>> pagamento(@PathVariable @NotNull(message = "O ID do cartão é obrigatório") Long id, @RequestBody @Valid PagamentoDto pagamentoDto){
		
		DetalhesCartaoDTO detalhesCartaoDTO = cartaoService.pagemento(id, pagamentoDto);
		 
		ApiResponse<DetalhesCartaoDTO> response = new ApiResponse<>(
				 HttpStatus.CREATED.value(),
				 	"Pagamento realizado com sucesso",
				 	detalhesCartaoDTO
				 );
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/{id}/fatura/pagamento")
	public ResponseEntity<ApiResponse<DetalhesCartaoDTO>> pagamentoFaturaCredito(@PathVariable @NotNull(message = "O ID do cartão é obrigatório") Long id){
		
		DetalhesCartaoDTO detalhesCartaoDTO = cartaoService.pagementoFaturaCredito(id);
		 
		ApiResponse<DetalhesCartaoDTO> response = new ApiResponse<>(
				 HttpStatus.CREATED.value(),
				 	"Pagamento realizado com sucesso",
				 	detalhesCartaoDTO
				 );
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deletePorId(@PathVariable @NotNull(message = "O ID da conta é obrigatório") Long id){
		
	    cartaoService.deletePorId(id);
		
		
		
	     return ResponseEntity.noContent().build();
	}
}
