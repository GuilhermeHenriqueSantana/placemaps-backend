package com.esoft.placemaps.placemaps.localizacao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemana;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Localizacao extends BasicClass {
    
    @Column(name = "pais", nullable = false, length = 50)
    private String pais;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "cidade", nullable = false, length = 50)
    private String cidade;

    @Column(name = "bairro", nullable = false, length = 50)
    private String bairro;

    @Column(name = "numero", nullable = false, length = 50)
    private String numero;

    @Column(name = "rua", nullable = false, length = 20)
    private String rua;

    @Column(name = "longitude", nullable = false)
    private Float longitude;

    @Column(name = "latitude", nullable = false)
    private Float latitude;

    @ManyToMany
    @JoinTable(
        name = "localizacao_dia_da_semana",
        joinColumns = @JoinColumn(name = "localizacao_id"),
        inverseJoinColumns = @JoinColumn(name = "dia_da_semana_id")
    )
    private List<DiaDaSemana> diasDaSemana;

}
