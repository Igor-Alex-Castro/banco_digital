package com.br.bancodigital.controollers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.bancodigital.dto.ApiResponse;
import com.br.bancodigital.dto.ApoliceDto;
import com.br.bancodigital.dto.SeguroDto;
import com.br.bancodigital.models.Seguro;
import com.br.bancodigital.services.SeguroSerivce;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/seguro")
@Validated
public class SeguroController {

	private final SeguroSerivce seguroService;
	
	public SeguroController(SeguroSerivce seguroService) {
		this.seguroService = seguroService;;
	}
	
	@PostMapping()
	public ResponseEntity<ApiResponse<SeguroDto >> salvar(@RequestBody @Valid SeguroDto seguroDto	){
		 
		SeguroDto seguroCriadoDto = seguroService.salvar(seguroDto	);
		
		ApiResponse<SeguroDto > response = new ApiResponse<SeguroDto>
		(HttpStatus.CREATED.value(), "Seguro adquirido com sucesso", seguroCriadoDto );

		return  ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@GetMapping("/{idApolice}")
	public ResponseEntity<ApiResponse<ApoliceDto>> obterApolicePorId(@PathVariable @NotNull(message = "O ID da apolice é obrigatório") Long idApolice){
		
		ApoliceDto apoliceDto = seguroService.obterApolice(idApolice);
		
		
		ApiResponse<ApoliceDto> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Apolice carregada com sucesso",
				 	apoliceDto 
				 );
		return ResponseEntity.ok( response);
	}
	
	@GetMapping()
	public ResponseEntity<ApiResponse<List<Seguro>>> obterSeguroDisponiveis(){
		
		List<Seguro> seguro = seguroService.obterSeguroDisponiveis();
		
		
		ApiResponse<List<Seguro>> response = new ApiResponse<>(
				 HttpStatus.OK.value(),
				 	"Apolice carregada com sucesso",
				 	seguro
				 );
		return ResponseEntity.ok( response);
	}
	
}
