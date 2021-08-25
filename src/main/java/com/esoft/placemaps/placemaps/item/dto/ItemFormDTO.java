package com.esoft.placemaps.placemaps.item.dto;

import java.math.BigDecimal;
import java.util.Objects;

import com.esoft.placemaps.placemaps.foto.Foto;
import com.esoft.placemaps.placemaps.item.Item;

import lombok.Getter;

@Getter
public class ItemFormDTO {
    
    private String descricao;
    private BigDecimal valor;
    private String fotoUrl = "";
    private String dadoSemanalId;

    public Item gerarItem() {
        return new Item().builder()
            .descricao(descricao)
            .valor(valor)
            .foto(fotoUrl.isEmpty() ? null : new Foto(fotoUrl))
        .build();
    }

}
