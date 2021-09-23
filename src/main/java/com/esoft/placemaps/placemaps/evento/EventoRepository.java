package com.esoft.placemaps.placemaps.evento;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventoRepository extends JpaRepository<Evento, String> {
    
    Optional<Evento> findByLocalizacaoId(String localizacaoId);

    List<Evento> findByPontoId(String pontoId);

    @Query(nativeQuery = true,
            value = "SELECT " +
                    "   e.* " +
                    "FROM " +
                    "   evento e " +
                    "WHERE " +
                    "   e.nome ILIKE CONCAT('%', :nome, '%')")
    Page<Evento> findAllByNomeContains(Pageable pageable,
                                         String nome);
}
