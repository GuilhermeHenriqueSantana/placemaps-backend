package com.esoft.placemaps.placemaps.avaliacao;

import com.esoft.placemaps.placemaps.ponto.Ponto;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, String> {

  Optional<Avaliacao> findFirstByPontoAndUsuario(Ponto ponto, Usuario usuario);

}
