package com.esoft.placemaps.placemaps.localizacao.dto;

import java.util.List;

import com.esoft.placemaps.placemaps.localizacao.Localizacao;

import lombok.Getter;

@Getter
public class LocalizacaoFormDTO {

    private String pais;
    private String estado;
    private String cidade;
    private String bairro;
    private String numero;
    private String rua;
    private Float longitude;
    private Float latitude;
    private List<String> diasDaSemanaIds;
    private String pontoId;

    public Localizacao gerarLocalizacao() {
        return new Localizacao().builder()
            .pais(pais).estado(estado).cidade(cidade)
            .bairro(bairro).numero(numero).rua(rua)
            .longitude(longitude).latitude(latitude)
        .build();
    }
    
}
