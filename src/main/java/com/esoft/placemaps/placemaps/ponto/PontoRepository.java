package com.esoft.placemaps.placemaps.ponto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

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
                  "   ) AS foto, " +
                  "   ( " +
                  "       SELECT " +
                  "         CAST((SUM(CAST(a.nota AS FLOAT)) / COUNT(a.nota)) as NUMERIC(3,2)) " +
                  "       FROM " +
                  "         avaliacao a " +
                  "       WHERE " +
                  "         a.ponto_id = p.id " +
                  "   ) AS nota " +
                  "FROM " +
                  "   ponto p " +
                  "INNER JOIN categoria c ON " +
                  "   c.id = p.categoria_id " +
                  "WHERE " +
                  "   p.nome ILIKE CONCAT('%',:nome,'%') " +
                  "   AND c.nome ILIKE CONCAT ('%',:categoria,'%') " +
                  "   AND p.ativo IS TRUE " +
                  "   ORDER BY p.id ",
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
  Page<Map<String, Object>> pontosPorNomeECategoria(Pageable pageable,
                                                    @Param("nome") String nome,
                                                    @Param("categoria") String categoria);

  @Query(nativeQuery = true,
          value = "SELECT " +
                  "   p.id, " +
                  "   p.nome, " +
                  "   p.ativo, " +
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
                  "   ) AS foto, " +
                  "   ( " +
                  "       SELECT " +
                  "         CAST((SUM(CAST(a.nota AS FLOAT)) / COUNT(a.nota)) as NUMERIC(3,2)) " +
                  "       FROM " +
                  "         avaliacao a " +
                  "       WHERE " +
                  "         a.ponto_id = p.id " +
                  "   ) AS nota, " +
                  "   c.id as categoriaId, " +
                  "   c.nome as categoria " +
                  "FROM " +
                  "   ponto p " +
                  "INNER JOIN categoria c ON " +
                  "   c.id = p.categoria_id " +
                  "WHERE " +
                  "   p.nome ILIKE CONCAT('%',:nome,'%') " +
                  "   AND c.nome ILIKE CONCAT ('%',:categoria,'%') " +
                  "   AND p.controle_ponto_id = :controleId " +
                  "   ORDER BY p.id ",
          countQuery = "SELECT " +
                  "   COUNT(DISTINCT p.id) " +
                  "FROM " +
                  "   ponto p " +
                  "INNER JOIN categoria c ON " +
                  "   c.id = p.categoria_id " +
                  "WHERE " +
                  "   p.nome ILIKE CONCAT('%',:nome,'%') " +
                  "   AND c.nome ILIKE CONCAT('%',:categoria,'%') " +
                  "   AND p.controle_ponto_id = :controleId ")
  Page<Map<String, Object>> pontosPorNomeCategoriaControle(Pageable pageable,
                                                           @Param("nome") String nome,
                                                           @Param("categoria") String categoria,
                                                           @Param("controleId") String controleId);

  @Query(nativeQuery = true,
          value = "SELECT " +
                  "   COUNT(*) > 0 " +
                  "FROM " +
                  "   ponto p " +
                  "WHERE " +
                  "   p.categoria_id = :categoriaId")
  Boolean existePontoComCategoriaId(@Param("categoriaId") String categoriaId);

  @Query(nativeQuery = true,
          value = "SELECT " +
                  "   COALESCE(COUNT(*), 0) " +
                  "FROM " +
                  "   ponto p " +
                  "WHERE " +
                  "   p.controle_ponto_id = :controleId " +
                  "   AND p.ativo IS TRUE")
  Integer obterQuantidadeDePontosAtivosPeloControle(@Param("controleId") String controleId);

}
