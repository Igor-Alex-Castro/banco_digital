package com.br.bancodigital.validations;

import com.br.bancodigital.dto.SalvarChavePixDto;
import com.br.bancodigital.enuns.TipoPix;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ChavePixValidator implements ConstraintValidator<ValidaChavePix, SalvarChavePixDto> {

	 @Override
	    public boolean isValid(SalvarChavePixDto dto, ConstraintValidatorContext context) {
	        if (dto == null || dto.chavePix() == null || dto.tipoPix() == null) {
	            return true; // deixa @NotNull e @NotBlank cuidarem disso
	        }

	        String chave = dto.chavePix().trim();
	        TipoPix tipo = dto.tipoPix();

	        boolean valido;
	        String mensagemErro;

	        switch (tipo) {
	            case CPF -> {
	                valido = chave.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
	                mensagemErro = "CPF inválido. Formato esperado: 000.000.000-00";
	            }
	            case CNPJ -> {
	                valido = chave.matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}");
	                mensagemErro = "CNPJ inválido. Formato esperado: 00.000.000/0000-00";
	            }
	            case CELULAR -> {
	                valido = chave.matches("\\(\\d{2}\\)\\s?9\\d{4}-\\d{4}") || chave.matches("\\+55\\d{11}");
	                mensagemErro = "Celular inválido. Formato esperado: (99) 99999-9999 ou +5599999999999";
	            }
	            case EMAIL -> {
	                valido = chave.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
	                mensagemErro = "E-mail inválido.";
	            }
	            
	            default -> {
	                valido = false;
	                mensagemErro = "Tipo de chave PIX inválido.";
	            }
	        }
	        
	        if (!valido) {
	            context.disableDefaultConstraintViolation();
	            context.buildConstraintViolationWithTemplate(mensagemErro)
	                    .addPropertyNode("chavePix")
	                    .addConstraintViolation();
	        }
	        return valido;    
	 	}
}

