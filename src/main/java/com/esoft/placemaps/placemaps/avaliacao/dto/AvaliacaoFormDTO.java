package com.esoft.placemaps.placemaps.avaliacao.dto;

import com.esoft.placemaps.placemaps.avaliacao.Avaliacao;
import lombok.Getter;

@Getter
public class AvaliacaoFormDTO {

    private Integer nota;
    private String descricao;
    private String pontoId;

    public Avaliacao gerarAvaliacao() {
        return new Avaliacao().builder()
                .nota(this.nota)
                .descricao(this.descricao)
                .build();
    }

}
