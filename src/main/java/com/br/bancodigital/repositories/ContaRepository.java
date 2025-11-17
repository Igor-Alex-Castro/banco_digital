package com.br.bancodigital.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.bancodigital.enuns.TipoConta;
import com.br.bancodigital.enuns.TipoPix;
import com.br.bancodigital.models.Cartao;
import com.br.bancodigital.models.Cliente;
import com.br.bancodigital.models.Conta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

	boolean existsByConta(String conta);
	
	long countByContaAndTipoConta(String conta, TipoConta tipoConta);

	List<Conta> findByClienteId(Long idCliente);

	long countByClienteIdAndTipoConta(Long id, TipoConta tipoConta);

	boolean existsByChavePix( String chavePix);

	boolean existsByChavePixAndContaNot(@NotBlank(message = "A chave pix deve ser obrigatória") String chavePix,
			String conta);



	Optional<Conta> findByChavePixAndTipopix(String chavePix, TipoPix tipoPix);

	Optional<Conta> findByContaAndAgencia( String conta, String agencia);

	Optional<Conta> findByCartao(Cartao cartao);


}
