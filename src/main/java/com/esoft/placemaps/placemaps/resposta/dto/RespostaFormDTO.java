package com.esoft.placemaps.placemaps.resposta.dto;

import com.esoft.placemaps.placemaps.resposta.Resposta;
import lombok.Getter;

import java.util.Date;

@Getter
public class RespostaFormDTO {

    private String descricao;
    private String comentarioId;

    public Resposta gerarResposta() {
        return new Resposta().builder()
                .descricao(this.descricao)
                .date(new Date())
                .build();
    }

}
