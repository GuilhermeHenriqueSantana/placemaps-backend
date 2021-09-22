package com.esoft.placemaps.placemaps.comentario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface ComentarioRepository extends JpaRepository<Comentario, String> {

  @Query(nativeQuery = true,
          value = "SELECT " +
                  "   c.id, " +
                  "   c.descricao, " +
                  "   c.data, " +
                  "   u.nome " +
                  "FROM " +
                  "   comentario c " +
                  "INNER JOIN usuario u ON " +
                  "   u.id = c.usuario_id " +
                  "WHERE " +
                  "   c.ponto_id = :pontoId ",
          countQuery = "SELECT " +
                        "   COUNT(distinct c.id) " +
                        "FROM " +
                        "   comentario c " +
                        "INNER JOIN usuario u ON " +
                        "   u.id = c.usuario_id " +
                        "WHERE " +
                        "   c.ponto_id = :pontoId ")
  Page<Map<String, Object>> obterComentariosPeloPonto(Pageable pageable,
                                                      @Param("pontoId") String pontoId);

}
