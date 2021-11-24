package com.esoft.placemaps.placemaps.item.dto;

import java.math.BigDecimal;
import java.util.Objects;

import com.esoft.placemaps.placemaps.dadosemanal.DadoSemanal;
import com.esoft.placemaps.placemaps.foto.Foto;
import com.esoft.placemaps.placemaps.item.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemFormDTO {
    
    private String descricao;
    private BigDecimal valor;
    private String fotoUrl = "";
    private String dadoSemanalId;
    private String id;

    public Item gerarItem() {
        Item item = Item.builder()
            .descricao(descricao)
            .valor(valor)
            .foto(fotoUrl.isEmpty() ? null : new Foto(fotoUrl))
        .build();
        if (Objects.nonNull(id) && !id.isEmpty()) {
            item.setId(id);
        }
        return item;
    }

}
