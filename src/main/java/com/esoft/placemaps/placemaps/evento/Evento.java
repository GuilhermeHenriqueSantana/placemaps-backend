package com.esoft.placemaps.placemaps.evento;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.placemaps.contato.Contato;
import com.esoft.placemaps.placemaps.foto.Foto;
import com.esoft.placemaps.placemaps.localizacao.Localizacao;
import com.esoft.placemaps.placemaps.ponto.Ponto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Evento extends BasicClass {

    @Column(name = "inicio", nullable = false)
    private Date inicio;

    @Column(name = "fim", nullable = false)
    private Date fim;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false, length = 1000)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "ponto_id")
    private Ponto ponto;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "localizacao_id")
    private Localizacao localizacao;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contato_id")
    private Contato contato;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "evento_foto",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "foto_id")
    )
    private List<Foto> fotos;

}
