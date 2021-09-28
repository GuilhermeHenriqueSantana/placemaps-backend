package com.esoft.placemaps.placemaps.avaliacao;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.placemaps.avaliacao.exception.AvaliacaoBadRequestException;
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
public class Avaliacao extends BasicClass {

    @Column(name = "nota", nullable = false)
    private Integer nota;

    @Column(name = "resposta", length = 1000)
    private String resposta;

    @Column(name = "descricao", length = 1000)
    private String descricao;

    @Column(name = "data")
    private Date data;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ponto_id", nullable = false)
    private Ponto ponto;

    public void validarAvaliacao() {
        if (this.nota < 0 || this.nota > 5) {
            throw new AvaliacaoBadRequestException("Nota inválida.");
        }
    }
}


