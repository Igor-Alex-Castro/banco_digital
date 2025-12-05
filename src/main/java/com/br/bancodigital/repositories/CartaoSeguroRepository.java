package com.br.bancodigital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.bancodigital.models.CartaoSeguro;

@Repository
public interface CartaoSeguroRepository  extends JpaRepository<CartaoSeguro, Long >{

	
	@Query("""
			
			SELECT COUNT(cs) > 0
			FROM CartaoSeguro cs
			WHERE cs.cartao.id = :cartaoId
			AND cs.seguro.id = :seguroId
			AND CURRENT_DATE BETWEEN cs.vigenciaIni AND cs.vigenciaFim
			""")
	boolean existeSeguroVigencia(
			@Param("cartaoId") Long cartaoId,
			@Param("seguroId") Long seguroId
			);

}
