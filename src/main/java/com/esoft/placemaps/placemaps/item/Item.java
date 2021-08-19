package com.esoft.placemaps.placemaps.item;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.placemaps.dadosemanal.DadoSemanal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item extends BasicClass{
    
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;

    @Column(name = "valor")
    private BigDecimal valor;

}
