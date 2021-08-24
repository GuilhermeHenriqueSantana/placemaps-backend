package com.esoft.placemaps.placemaps.diadasemana;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.esoft.placemaps.configuration.basicclass.BasicClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DiaDaSemana extends BasicClass {
   
    @Column(name = "nome_dia_semana", nullable = false, length = 7)
    @Enumerated(EnumType.STRING)
    private NomeDiaSemana nomeDiaSemana;

}
