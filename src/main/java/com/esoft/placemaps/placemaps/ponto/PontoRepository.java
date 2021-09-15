package com.esoft.placemaps.placemaps.ponto;

import com.esoft.placemaps.placemaps.ponto.projection.PontoPageProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PontoRepository extends JpaRepository<Ponto, String> {

  @Query(nativeQuery = true,
          value = "SELECT " +
                  "   p.id, " +
                  "   p.nome, " +
                  "   ( " +
                  "       SELECT " +
                  "         f.url " +
                  "       FROM " +
                  "         foto f " +
                  "       INNER JOIN ponto_foto pf ON " +
                  "         pf.foto_id = f.id " +
                  "       WHERE " +
                  "         pf.ponto_id = p.id " +
                  "       LIMIT 1 " +
                  "   ) as foto " +
                  "FROM " +
                  "   ponto p " +
                  "INNER JOIN categoria c ON " +
                  "   c.id = p.categoria_id " +
                  "WHERE " +
                  "   p.nome ILIKE CONCAT('%',:nome,'%') " +
                  "   AND c.nome ILIKE CONCAT ('%',:categoria,'%') " +
                  "   AND p.ativo IS TRUE ",
          countQuery = "SELECT " +
                  "   COUNT(DISTINCT p.id) " +
                  "FROM " +
                  "   ponto p " +
                  "INNER JOIN categoria c ON " +
                  "   c.id = p.categoria_id " +
                  "WHERE " +
                  "   p.nome ILIKE CONCAT('%',:nome,'%') " +
                  "   AND c.nome ILIKE CONCAT('%',:categoria,'%') " +
                  "   AND p.ativo IS TRUE ")
  Page<PontoPageProjection> pontoPorNomeECategoria(Pageable pageable,
                                                   @Param("nome") String nome,
                                                   @Param("categoria") String categoria);

}
