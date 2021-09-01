package com.esoft.placemaps.placemaps.localizacao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, String> {

    Optional<Localizacao> findByPontoId(String pontoId);
    
}
