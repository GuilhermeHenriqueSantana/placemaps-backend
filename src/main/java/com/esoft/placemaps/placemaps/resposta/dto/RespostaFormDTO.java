package com.esoft.placemaps.placemaps.resposta.dto;

import com.esoft.placemaps.placemaps.resposta.Resposta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RespostaFormDTO {

    private String descricao;
    private String comentarioId;
    private String id;

    public Resposta gerarResposta() {
        Resposta resposta = Resposta.builder()
                .descricao(this.descricao)
                .date(new Date())
                .build();
        if (Objects.isNull(id) && !id.isEmpty()) {
            resposta.setId(id);
        }
        return resposta;
    }

}
