package com.esoft.placemaps.placemaps.categoria;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.esoft.placemaps.configuration.basicclass.BasicClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Categoria extends BasicClass {
 
    @Column(name = "nome", nullable = false)
    private String nome;
    
}
