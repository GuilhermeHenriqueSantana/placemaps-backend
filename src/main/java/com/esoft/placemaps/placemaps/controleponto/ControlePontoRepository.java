package com.esoft.placemaps.placemaps.controleponto;

import com.esoft.placemaps.placemaps.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface ControlePontoRepository extends JpaRepository<ControlePonto, String> {

    ControlePonto findFirstByUsuario(Usuario usuario);

    @Query(nativeQuery = true,
            value = "SELECT " +
                    "   c.id, " +
                    "   c.pontos_ativos ativos, " +
                    "   c.pontos_solicitados solicitados " +
                    "FROM " +
                    "   controle_ponto c " +
                    "WHERE " +
                    "   c.usuario_id = :proprietarioId")
    Map<String, Object> obterPeloProprietario(@Param("proprietarioId") String proprietarioId);

}
