package com.esoft.placemaps.placemaps.evento;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventoRepository extends JpaRepository<Evento, String> {
    
    Optional<Evento> findByLocalizacaoId(String localizacaoId);

    List<Evento> findByPontoId(String pontoId);

    @Query(nativeQuery = true,
            value = "SELECT " +
                    "   e.* " +
                    "FROM " +
                    "   evento e " +
                    "WHERE " +
                    "   e.nome ILIKE CONCAT('%', :nome, '%')",
            countQuery = "SELECT " +
                    "           COUNT(DISTINCT e.id) " +
                    "       FROM " +
                    "           evento e " +
                    "       WHERE " +
                    "           e.nome ILIKE CONCAT('%', :nome, '%') ")
    Page<Evento> findAllByNomeContains(Pageable pageable,
                                       String nome);

    @Query(nativeQuery = true,
           value = "SELECT " + 
                        "e.id, " +
                        "e.inicio, " +
                        "e.fim, " +
                        "e.nome " +
                    "FROM " +
                        "evento e " +
                     "INNER JOIN usuario_evento ue ON " +
                        "ue.evento_id = e.id " +
                     "WHERE " + 
                        "ue.usuario_id = :usuarioId " + 
                     "AND e.fim >= NOW()",
           countQuery = "SELECT " +
                                "COUNT(DISTINCT e.id) " +
                        "FROM " +
                                "evento e " +
                        "INNER JOIN usuario_evento ue ON " +
                                "ue.evento_id = e.id " +
                        "WHERE " + 
                                "ue.usuario_id = :usuarioId " + 
                        "AND e.fim >= NOW()")
    Page<Map<String, Object>> obterEventosDefinidoComoLembrete(Pageable pageable, @Param("usuarioId") String usuarioId);
}
