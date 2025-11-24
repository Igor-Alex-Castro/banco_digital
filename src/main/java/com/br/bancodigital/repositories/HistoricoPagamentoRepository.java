package com.br.bancodigital.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.bancodigital.enuns.TipoCartao;
import com.br.bancodigital.models.HistoricoPagamento;



@Repository
public interface  HistoricoPagamentoRepository extends JpaRepository<HistoricoPagamento, Long> {

	boolean existsByNumeroCod(String numeroCod);

	@Query("""
		    SELECT SUM(h.valor)
		    FROM HistoricoPagamento h
		    WHERE h.conta.cartao.id = :idCartao
		      AND h.dataPagamento BETWEEN :dataInicial AND :dataFinal
		""")
		Optional<BigDecimal> somarPorCartao(
		        @Param("idCartao") Long idCartao,
		        @Param("dataInicial") LocalDate dataInicial,
		        @Param("dataFinal") LocalDate dataFinal
		);
	
	@Query("""
		    SELECT SUM(h.valor)
		    FROM HistoricoPagamento h
		    WHERE h.conta.cartao.id = :idCartao
		      AND h.paga = :paga
		""")
		BigDecimal faturaPorCartaoNaoPaga(
		        @Param("idCartao") Long idCartao,
		        @Param("paga") Boolean paga
		);
	
	@Query("""
		    SELECT SUM(h.valor)
		    FROM HistoricoPagamento h
		    WHERE h.conta.cartao.id = :idCartao
		      AND h.dataPagamento = :dataHoje
		""")
		Optional<BigDecimal> limiteDiarioPorCartaoDebito(
		        @Param("idCartao") Long idCartao,
		        @Param("dataHoje") LocalDate dataHoje
		       
		);
	
	
	@Query("""
			
			SELECT h
			from HistoricoPagamento h
			WHERE h.tipoCartao = :tipoCartao
			AND h.conta.cartao.id = :idCartao
			AND h.dataPagamento BETWEEN :dataInicial AND :dataFinal
			AND paga = false
			
			""")
		List<HistoricoPagamento> listaPagaentoCredito(
				@Param("tipoCartao") TipoCartao itipoCartao,
				 @Param("idCartao") Long idCartao,
		        @Param("dataInicial") LocalDate dataInicial,
		        @Param("dataFinal") LocalDate dataFinal
				);

}
