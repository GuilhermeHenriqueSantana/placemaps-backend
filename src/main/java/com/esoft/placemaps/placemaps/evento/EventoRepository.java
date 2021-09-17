package com.esoft.placemaps.placemaps.evento;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, String> {
    
    Optional<Evento> findByLocalizacaoId(String localizacaoId);

    List<Evento> findByPontoId(String pontoId);

}
