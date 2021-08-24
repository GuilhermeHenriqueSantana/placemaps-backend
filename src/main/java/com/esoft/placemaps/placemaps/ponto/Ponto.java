package com.esoft.placemaps.placemaps.ponto;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.esoft.placemaps.configuration.basicclass.BasicClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ponto extends BasicClass {
    
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", length = 1000)
    private String descricao;

    @Column(name = "subtitulo")
    private String subTitulo;

    @Column(name = "fixo", nullable = false)
    private Boolean fixo;

    /*
    ligação com 
    evento
    contato
    foto
    comentario
    avaliacao
    categoria  
    */

}
