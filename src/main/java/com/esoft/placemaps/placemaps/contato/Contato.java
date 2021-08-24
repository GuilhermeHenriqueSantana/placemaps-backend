package com.esoft.placemaps.placemaps.contato;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Contato extends BasicClass {

    @Column(name = "telefone", length = 25)
    private String telefone;

    @Column(name = "whatsapp", length = 25)
    private String whatsapp;

}
