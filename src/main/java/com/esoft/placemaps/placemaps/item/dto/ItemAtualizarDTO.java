package com.esoft.placemaps.placemaps.item.dto;

import java.math.BigDecimal;
import java.util.Objects;

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
public class ItemAtualizarDTO {
    
    private String descricao;
    private BigDecimal valor;
    private String fotoUrl = "";

    public Item atualizarItem(Item item) {
        item.setDescricao(descricao);
        item.setValor(valor);
        item.setFoto(fotoUrl.isEmpty() ? null : new Foto(fotoUrl));
        return item;
    }

}
