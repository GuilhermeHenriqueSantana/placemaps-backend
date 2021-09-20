package com.esoft.placemaps.placemaps.dadosemanal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DadoSemanalRepository extends JpaRepository<DadoSemanal, String> {

  @Query(nativeQuery = true,
          value = "SELECT " +
                  "   d.nome " +
                  "FROM " +
                  "   dado_semanal d " +
                  "INNER JOIN dado_semanal_dia_da_semana dd ON " +
                  "   dd.dado_semanal_id = d.id " +
                  "INNER JOIN dia_da_semana ds ON " +
                  "   ds.id = dd.dia_da_semana_id " +
                  "WHERE " +
                  "   ponto_id = :pontoId " +
                  "   AND ds.nome_dia_semana = :nomeDiaSemana ")
  List<String> obterNomesPorPontoId(@Param("pontoId") String pontoId,
                                    @Param("nomeDiaSemana") String nomeDiaSemana);

  Page<DadoSemanal> findDadoSemanalsByPontoId(Pageable pageable, String pontoId);
}
