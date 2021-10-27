package com.esoft.placemaps.placemaps.dadosemanal.dto;

import java.sql.Time;
import java.util.List;

import com.esoft.placemaps.placemaps.dadosemanal.DadoSemanal;

import lombok.Getter;

@Getter
public class DadoSemanalAtualizarDTO {
    
    private String nome;
    private Time horaInicio;
    private Time horaFim;
    private String descricao;
    private Boolean possuiValor;
    private List<String> diasDaSemanaIds;

    public DadoSemanal atualizarDadoSemanal(DadoSemanal dadoSemanal) {
        dadoSemanal.setNome(nome);
        dadoSemanal.setHoraInicio(horaInicio);
        dadoSemanal.setHoraFim(horaFim);
        dadoSemanal.setDescricao(descricao);
        dadoSemanal.setPossuiValor(possuiValor);
        return dadoSemanal;
    }

}
