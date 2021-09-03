package com.esoft.placemaps.placemaps.localizacao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, String> {

    Optional<List<Localizacao>> findByPontoId(String pontoId);
    
}
