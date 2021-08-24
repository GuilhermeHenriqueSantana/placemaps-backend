package com.esoft.placemaps.placemaps.evento;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.placemaps.contato.Contato;
import com.esoft.placemaps.placemaps.localizacao.Localizacao;
import com.esoft.placemaps.placemaps.ponto.Ponto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

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

    @OneToOne
    @JoinColumn(name = "ponto_id")
    private Ponto ponto;

    @OneToOne
    @JoinColumn(name = "localizacao_id")
    private Localizacao localizacao;

    @OneToOne
    @JoinColumn(name = "contato_id")
    private Contato contato;

}
