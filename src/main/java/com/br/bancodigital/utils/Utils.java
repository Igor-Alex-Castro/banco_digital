package com.br.bancodigital.utils;

import java.time.LocalDate;
import java.time.Period;

public class Utils {

	public static boolean isValidCPF(String cpf) {
	    if (cpf == null) return false;

	    // Remove caracteres não numéricos
	    cpf = cpf.replaceAll("\\D", "");

	    // Verifica se tem 11 dígitos
	    if (cpf.length() != 11) return false;

	    // Verifica se todos os dígitos são iguais
	    if (cpf.matches("(\\d)\\1{10}")) return false;

	    try {
	        int soma = 0;
	        // Cálculo do primeiro dígito verificador
	        for (int i = 0; i < 9; i++) {
	            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
	        }
	        int resto = soma % 11;
	        int digito1 = (resto < 2) ? 0 : 11 - resto;

	        // Cálculo do segundo dígito verificador
	        soma = 0;
	        for (int i = 0; i < 9; i++) {
	            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
	        }
	        soma += digito1 * 2;

	        resto = soma % 11;
	        int digito2 = (resto < 2) ? 0 : 11 - resto;

	        // Verifica se os dois dígitos conferem
	        return digito1 == Character.getNumericValue(cpf.charAt(9)) &&
	               digito2 == Character.getNumericValue(cpf.charAt(10));
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	public static boolean validaMaiorIdade( LocalDate dataNasce) {
		if( dataNasce == null) {
			throw new IllegalArgumentException("A data de nascimento não pode ser nula");
		}
		
		LocalDate hoje = LocalDate.now();
	    int idade = Period.between(dataNasce, hoje).getYears();

		return  idade >= 18;
	}

}
