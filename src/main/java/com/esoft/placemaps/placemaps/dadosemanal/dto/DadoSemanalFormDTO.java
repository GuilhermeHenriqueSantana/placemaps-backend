package com.esoft.placemaps.placemaps.dadosemanal.dto;

import java.sql.Time;
import java.util.List;
import java.util.Objects;

import com.esoft.placemaps.placemaps.dadosemanal.DadoSemanal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DadoSemanalFormDTO {
   
    private String nome;
    private Time horaInicio;
    private Time horaFim;
    private String descricao;
    private Boolean possuiValor;
    private String pontoId;
    private List<String> diasDaSemanaIds;
    private String id;

    public DadoSemanal gerarDadoSemanal() {
        DadoSemanal dadoSemanal = DadoSemanal.builder()
            .nome(nome)
            .horaInicio(horaInicio)
            .horaFim(horaFim)
            .descricao(descricao)
            .possuiValor(possuiValor)
            .build();
        if (Objects.nonNull(id) && !id.isEmpty()) {
            dadoSemanal.setId(id);
        }
        return dadoSemanal;
    }
    
}
