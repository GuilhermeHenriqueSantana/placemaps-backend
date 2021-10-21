package com.esoft.placemaps.placemaps.avaliacao;

import com.esoft.placemaps.placemaps.ponto.Ponto;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;
import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, String> {

  Optional<Avaliacao> findFirstByPontoAndUsuario(Ponto ponto, Usuario usuario);

  @Query(nativeQuery = true,
          value = "SELECT " +
                  "   a.id, " +
                  "   a.descricao, " +
                  "   a.data, " +
                  "   a.nota," +
                  "   u.nome  " +
                  "FROM " +
                  "   avaliacao a " +
                  "INNER JOIN usuario u ON " +
                  "   u.id = a.usuario_id " +
                  "WHERE " +
                  "   a.ponto_id = :pontoId ",
          countQuery = "SELECT " +
                  "   COUNT(distinct a.id) " +
                  "FROM " +
                  "   avaliacao a " +
                  "INNER JOIN usuario u ON " +
                  "   u.id = a.usuario_id " +
                  "WHERE " +
                  "   a.ponto_id = :pontoId ")
  Page<Map<String, Object>> obterAvaliacoesPeloPonto(Pageable pageable,
                                                      @Param("pontoId") String pontoId);

  @Query(nativeQuery = true,
          value = "SELECT " +
                  "   COALESCE(COUNT(*)) " +
                  "FROM " +
                  "   avaliacao a " +
                  "INNER JOIN ponto p ON " +
                  "   p.id = a.ponto_id " +
                  "WHERE " +
                  "   p.controle_ponto_id = :controleId")
  Integer obterQtdeAvaliacoesPeloControleDePonto(@Param("controleId") String controleId);

}
