package com.esoft.placemaps.placemaps.dadosemanal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface DadoSemanalRepository extends JpaRepository<DadoSemanal, String> {

  @Query(nativeQuery = true,
        value = "SELECT " +
                "   d.id, " +
                "   d.nome, " +
                "   d.hora_inicio, " +
                "   d.hora_fim, " +
                "   d.descricao, " +
                "   d.possui_valor " +
                "FROM " +
                "   dado_semanal d " +
                "WHERE " +
                "   d.ponto_id = :pontoId ",
        countQuery = "SELECT " +
                "         COUNT(DISTINCT d.id) " +
                "     FROM " +
                "         dado_semanal d " +
                "     WHERE " +
                "       d.ponto_id = :pontoId ")
  Page<Map<String, Object>> findDadoSemanalsByPontoId(Pageable pageable, String pontoId);

}
