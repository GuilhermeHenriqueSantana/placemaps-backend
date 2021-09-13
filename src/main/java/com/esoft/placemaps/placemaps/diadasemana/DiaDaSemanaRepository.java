package com.esoft.placemaps.placemaps.diadasemana;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiaDaSemanaRepository extends JpaRepository<DiaDaSemana, String>{
    
    @Query("SELECT " +
           "    d " +
           "FROM " +
           "    DiaDaSemana d " + 
           "WHERE " + 
           "    d.id IN :ids")
    List<DiaDaSemana> obterDiasDaSemanaPorIds(@Param("ids") List<String> ids);

}
