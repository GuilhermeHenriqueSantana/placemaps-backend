package com.esoft.placemaps.placemaps.usuario;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario extends BasicClass {
    @NotNull
    private String nome;
    @NotNull
    private String email;
    @NotNull
    private String senha;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
    private String numeracaoDocumento;
}

