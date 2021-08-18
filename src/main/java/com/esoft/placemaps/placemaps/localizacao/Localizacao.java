package com.esoft.placemaps.placemaps.localizacao;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.esoft.placemaps.configuration.basicclass.BasicClass;

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

    //ligação com diadasemana

}
