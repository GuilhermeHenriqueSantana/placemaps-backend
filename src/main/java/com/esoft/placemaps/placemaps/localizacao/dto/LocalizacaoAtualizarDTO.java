package com.esoft.placemaps.placemaps.localizacao.dto;

import java.util.List;

import com.esoft.placemaps.placemaps.localizacao.Localizacao;

import lombok.Getter;

@Getter
public class LocalizacaoAtualizarDTO {
    
    private String pais;
    private String estado;
    private String cidade;
    private String bairro;
    private String numero;
    private String rua;
    private Float longitude;
    private Float latitude;
    private List<String> diasDaSemanaIds;

    public Localizacao atualizarLocalizacao(Localizacao localizacao) {
        localizacao.setPais(pais);
        localizacao.setEstado(estado);
        localizacao.setCidade(cidade);
        localizacao.setBairro(bairro);
        localizacao.setNumero(numero);
        localizacao.setRua(rua);
        localizacao.setLongitude(longitude);
        localizacao.setLatitude(latitude);
        return localizacao;
    }

}
