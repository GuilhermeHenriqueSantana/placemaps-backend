package com.esoft.placemaps.placemaps.dadosemanal.dto;

import java.sql.Time;
import java.util.List;

import com.esoft.placemaps.placemaps.dadosemanal.DadoSemanal;

import lombok.Getter;

@Getter
public class DadoSemanalFormDTO {
   
    private String nome;
    private Time horaInicio;
    private Time horaFim;
    private String descricao;
    private Boolean possuiValor;
    private String pontoId;
    private List<String> diasDaSemanaIds;

    public DadoSemanal gerarDadoSemanal() {
        return DadoSemanal.builder()
            .nome(nome)
            .horaInicio(horaInicio)
            .horaFim(horaFim)
            .descricao(descricao)
            .possuiValor(possuiValor)
            .build();
    }
    
}
