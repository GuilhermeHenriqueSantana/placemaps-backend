package com.esoft.placemaps.placemaps.resposta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface RespostaRepository extends JpaRepository<Resposta, String> {

  @Query(nativeQuery = true,
          value = "SELECT " +
                  "   r.id, " +
                  "   r.descricao, " +
                  "   r.data, " +
                  "   u.nome " +
                  "FROM " +
                  "   resposta r " +
                  "INNER JOIN usuario u ON " +
                  "   u.id = r.usuario_id " +
                  "WHERE " +
                  "   r.comentario_id = :comentarioId ",
          countQuery = "SELECT " +
                  "   COUNT(distinct r.id) " +
                  "FROM " +
                  "   resposta r " +
                  "INNER JOIN usuario u ON " +
                  "   u.id = r.usuario_id " +
                  "WHERE " +
                  "   r.comentario_id = :comentarioId ")
  Page<Map<String, Object>> obterRespostasPeloPonto(Pageable pageable,
                                                      @Param("comentarioId") String comentarioId);

}
