package com.esoft.placemaps.placemaps.foto;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Foto extends BasicClass {

    @Column(name = "url", nullable = false)
    private String url;

}
