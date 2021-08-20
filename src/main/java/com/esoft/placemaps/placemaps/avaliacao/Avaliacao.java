package com.esoft.placemaps.placemaps.avaliacao;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.placemaps.ponto.Ponto;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import lombok.*;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Avaliacao extends BasicClass {

    @Column(name = "nota", nullable = false)
    private Integer nota;

    @Column(name = "resposta", length = 1000)
    private String resposta;

    @Column(name = "descricao", length = 1000)
    private String descricao;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Lazy
    @ManyToOne
    @JoinColumn(name = "ponto_id", nullable = false)
    private Ponto ponto;

}


