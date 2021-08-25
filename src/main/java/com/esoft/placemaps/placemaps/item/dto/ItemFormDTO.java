package com.esoft.placemaps.placemaps.item.dto;

import java.math.BigDecimal;

import com.esoft.placemaps.placemaps.foto.Foto;
import com.esoft.placemaps.placemaps.item.Item;

import lombok.Getter;

@Getter
public class ItemFormDTO {
    
    private String descricao;
    private BigDecimal valor;
    private Foto foto;
    private String dadoSemanalId;

    public Item gerarItem() {
        return new Item().builder()
            .descricao(descricao)
            .valor(valor)
            .foto(foto)
        .build();
    }

}
