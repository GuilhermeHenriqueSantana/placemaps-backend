package com.esoft.placemaps.placemaps.item;

import java.math.BigDecimal;

import javax.persistence.*;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.placemaps.dadosemanal.DadoSemanal;

import com.esoft.placemaps.placemaps.foto.Foto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
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

    @ManyToOne
    @JoinColumn(name = "dado_semanal_id", nullable = false)
    private DadoSemanal dadoSemanal;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "foto_id")
    private Foto foto;

}
