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

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DiaDaSemana extends BasicClass {
   
    @Column(name = "nome_dia_semana", nullable = false, length = 7)
    @Enumerated(EnumType.STRING)
    private NomeDiaSemana nomeDiaSemana;

    public NomeDiaSemana pegarDiaDaSemana(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
        switch (diaSemana) {
            case 1:
                return NomeDiaSemana.DOMINGO;
            case 2:
                return NomeDiaSemana.SEGUNDA;
            case 3:
                return NomeDiaSemana.TERCA;
            case 4:
                return NomeDiaSemana.QUARTA;
            case 5:
                return NomeDiaSemana.QUINTA;
            case 6:
                return NomeDiaSemana.SEXTA;
            default:
                return NomeDiaSemana.SABADO;
        }
    }
}
