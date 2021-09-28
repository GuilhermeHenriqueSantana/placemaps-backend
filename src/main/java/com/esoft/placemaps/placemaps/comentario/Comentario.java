package com.esoft.placemaps.placemaps.comentario;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.placemaps.ponto.Ponto;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import lombok.*;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comentario extends BasicClass {

    @Column(name = "descricao", length = 1000)
    private String descricao;

    @Column(name = "data")
    private Date data = new Date();

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ponto_id", nullable = false)
    private Ponto ponto;

}


