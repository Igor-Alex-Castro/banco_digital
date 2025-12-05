package com.br.bancodigital.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.bancodigital.enuns.TipoSeguro;
import com.br.bancodigital.models.Seguro;

@Repository
public interface SeguroRepository extends JpaRepository<Seguro, Long> {

	List<Seguro> findByTipoSeguroIn(List<TipoSeguro> tipoSeguro);

	/*
	 * @Query(""" SELECT COUNT(s) > 0 FROM Seguro s WHERE s.cartao.id = :cartaoId
	 * AND s.tipoSeguro = :tipo AND CURRENT_DATE BETWEEN s.vigenciaIni AND
	 * s.vigenciaFim """) boolean existeSeguroVigente( @Param("cartaoId") Long
	 * cartaoId,
	 * 
	 * @Param("tipo") TipoSeguro tipo );
	 */

}
