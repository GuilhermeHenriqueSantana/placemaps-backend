package com.esoft.placemaps.placemaps.localizacao.dto;

import java.util.List;
import java.util.Objects;

import com.esoft.placemaps.placemaps.localizacao.Localizacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private String id;

    public Localizacao gerarLocalizacao() {
        Localizacao localizacao = Localizacao.builder()
            .pais(pais).estado(estado).cidade(cidade)
            .bairro(bairro).numero(numero).rua(rua)
            .longitude(longitude).latitude(latitude)
            .build();
        if (Objects.nonNull(id) && !id.isEmpty()) {
            localizacao.setId(id);
        }
        return localizacao;
    }
    
}
