package com.esoft.placemaps.placemaps.avaliacao.dto;

import com.esoft.placemaps.placemaps.avaliacao.Avaliacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoFormDTO {

    private Integer nota;
    private String descricao;
    private String pontoId;
    private String id;

    public Avaliacao gerarAvaliacao() {
        Avaliacao avaliacao = new Avaliacao().builder()
                .nota(this.nota)
                .descricao(this.descricao)
                .data(new Date())
                .build();
        if (Objects.nonNull(id) && !id.isEmpty()) {
            avaliacao.setId(id);
        }
        return avaliacao;
    }

}
