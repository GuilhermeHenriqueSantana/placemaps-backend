package com.esoft.placemaps.placemaps.resposta;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.placemaps.comentario.Comentario;
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
public class Resposta extends BasicClass {

    @Column(name = "descricao", length = 1000)
    private String descricao;

    @Column(name = "data")
    private Date date;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Lazy
    @ManyToOne
    @JoinColumn(name = "comentario_id", nullable = false)
    private Comentario comentario;

}
