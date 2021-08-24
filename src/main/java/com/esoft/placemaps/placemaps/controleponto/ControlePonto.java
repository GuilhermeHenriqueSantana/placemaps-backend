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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "controle_ponto_ponto",
            joinColumns = @JoinColumn(name = "controle_ponto_id"),
            inverseJoinColumns = @JoinColumn(name = "ponto_id")
    )
    private List<Ponto> pontos;

}