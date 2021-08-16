package com.esoft.placemaps.plano;

import com.esoft.placemaps.basicclass.BasicClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Plano extends BasicClass {
    @NotNull
    private String nome;
    @NotNull
    private String descricao;
    @NotNull
    private BigDecimal valor;
}

