package com.esoft.placemaps.placemaps.controleponto;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.placemaps.ponto.Ponto;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ControlePonto extends BasicClass {

    @Column(name = "pontos_ativos", nullable = false)
    private Integer pontosAtivos;

    @Column(name = "pontos_solicitados", nullable = false)
    private Integer pontosSolicitados;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany
    @JoinColumn(name = "controle_ponto_id", nullable = false)
    private List<Ponto> pontos;

}