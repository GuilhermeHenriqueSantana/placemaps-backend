package com.esoft.placemaps.placemaps.localizacao;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, String> {

    @Query(nativeQuery = true,
            value = "SELECT " + 
                    "   l.*, lp.ponto_id " +
                    "FROM " +
                    "   localizacao l " +
                    "INNER JOIN localizacao_dia_da_semana ld " +
                    "   ON l.id = ld.localizacao_id " +
                    "INNER JOIN dia_da_semana d " +
                    "   ON d.id = ld.dia_da_semana_id " +
                    "INNER JOIN localizacao_ponto  lp " +
                    "   ON lp.localizacao_id  = l.id " +
                    "WHERE " +
                    "   lp.ponto_id = :pontoId " +
                    "   AND d.nome_dia_semana = :nomeDiaSemana " +
                    "LIMIT 1")
    Optional<Localizacao> obterPorPontoEDiaDaSemana(@Param("pontoId") String pontoId,
                                                    @Param("nomeDiaSemana") String nomeDiaSemana);

    @Query(nativeQuery = true,
            value = "SELECT " +
                    "   l.* " +
                    "FROM " +
                    "   localizacao l " +
                    "INNER JOIN localizacao_ponto lp ON " +
                    "   lp.localizacao_id = l.id " +
                    "WHERE " +
                    "   lp.ponto_id = :pontoId",
            countQuery = "SELECT " +
                    "   COUNT(l.*) " +
                    "FROM " +
                    "   localizacao l " +
                    "INNER JOIN localizacao_ponto lp ON " +
                    "   lp.localizacao_id = l.id " +
                    "WHERE " +
                    "   lp.ponto_id = :pontoId")
    Page<Map<String, Object>> obterLocalizacoesPeloPontoId(Pageable pageable, @Param("pontoId") String pontoId);
}
