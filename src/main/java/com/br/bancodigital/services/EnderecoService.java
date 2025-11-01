package com.br.bancodigital.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.br.bancodigital.dto.EnderecoDto;
import com.br.bancodigital.exceptions.BadRequestException;
import com.br.bancodigital.exceptions.BusinessException;


@Service
public class EnderecoService {

	final private String ENDPOINT = "https://viacep.com.br/ws/";

	public EnderecoDto buscarEnderecoPorCep(String cep) {

		String url = ENDPOINT + cep + "/json/";
		RestTemplate rest = new RestTemplate();

		EnderecoDto enderecoDto = null;
		try {
			enderecoDto = rest.getForObject(url, EnderecoDto.class);
		} catch (HttpClientErrorException e) {
			throw new  BadRequestException("CEP não encontrado");
		} catch (Exception e) {
			throw new BusinessException("Erro ao consultar o CEP: " + e.getMessage());
		}

		if (enderecoDto.erro() != null && Boolean.valueOf(enderecoDto.erro())) {
			throw new BusinessException("Este CEP não é valido");
		}

		return enderecoDto;

	}
}
