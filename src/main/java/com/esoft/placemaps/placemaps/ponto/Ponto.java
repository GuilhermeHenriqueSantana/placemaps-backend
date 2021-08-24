package com.esoft.placemaps.placemaps.ponto;

import javax.persistence.*;

import com.esoft.placemaps.configuration.basicclass.BasicClass;

import com.esoft.placemaps.placemaps.contato.Contato;
import com.esoft.placemaps.placemaps.foto.Foto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @OneToOne
    @JoinColumn(name = "contato_id")
    private Contato contato;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "ponto_foto",
            joinColumns = @JoinColumn(name = "ponto_id"),
            inverseJoinColumns = @JoinColumn(name = "foto_id")
    )
    private List<Foto> fotos;

}
