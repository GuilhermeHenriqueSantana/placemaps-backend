package com.esoft.placemaps.placemaps.comentario.dto;

import com.esoft.placemaps.placemaps.comentario.Comentario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioFormDTO {

    private String descricao;
    private String pontoId;
    private String id;

    public Comentario gerarComentario() {
        Comentario comentario = new Comentario().builder()
                .descricao(this.descricao)
                .data(new Date())
                .build();
        if (Objects.nonNull(id) && !id.isEmpty()) {
            comentario.setId(id);
        }
        return comentario;
    }

}
