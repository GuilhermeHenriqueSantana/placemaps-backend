package com.esoft.placemaps.placemaps.comentario.dto;

import com.esoft.placemaps.placemaps.comentario.Comentario;
import lombok.Getter;

@Getter
public class ComentarioFormDTO {

    private String descricao;
    private String pontoId;

    public Comentario gerarComentario() {
        return new Comentario().builder()
                .descricao(this.descricao)
                .build();
    }

}
