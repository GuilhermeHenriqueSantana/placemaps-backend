package com.esoft.placemaps.placemaps.item;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, String> {
    
    @Query(
        nativeQuery = true,
        value = "SELECT " +
                    "i.id, " +
                    "i.descricao, " +
                    "i.valor, " +
                    "f.url " +
                "FROM " +
                    "item i " + 
                "LEFT JOIN foto f ON " +
                    "f.id = i.foto_id " +
                "WHERE i.dado_semanal_id = :dadoSemanalId",
        countQuery = "SELECT " +
                        "COUNT(DISTINCT i.id) " +
                    "FROM " +
                        "item i " + 
                    "LEFT JOIN foto f ON " +
                        "f.id = i.foto_id " +
                    "WHERE i.dado_semanal_id = :dadoSemanalId")
    Page<Map<String, Object>> findByDadoSemanalId(Pageable pageable, @Param("dadoSemanalId") String dadoSemanalId);

}
