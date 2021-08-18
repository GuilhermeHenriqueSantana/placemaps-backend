package com.esoft.placemaps.placemaps.ponto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.placemaps.localizacao.Localizacao;

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

    @Column(name = "fixo")
    private Boolean fixo;

    @OneToMany
    @JoinTable(
        name = "ponto_localizacao",
        joinColumns = @JoinColumn(name = "ponto_id"),
        inverseJoinColumns = @JoinColumn(name = "localizacao_id")
    )
    private List<Localizacao> localizacoes;
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
