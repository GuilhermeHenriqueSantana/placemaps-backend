package com.esoft.placemaps.placemaps.usuario;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.helpers.DocumentoHelper;
import com.esoft.placemaps.helpers.EmailHelper;
import com.esoft.placemaps.helpers.SenhaHelper;
import com.esoft.placemaps.placemaps.evento.Evento;
import com.esoft.placemaps.placemaps.foto.Foto;
import com.esoft.placemaps.placemaps.pedidocadastro.exception.PedidoCadastroBadRequestException;
import com.esoft.placemaps.placemaps.usuario.exception.UsuarioBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario extends BasicClass {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "tipo_usuario", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @Column(name = "numeracao_documento", length = 14)
    private String numeracaoDocumento;

    @ManyToMany
    @JoinTable(
            name = "usuario_evento",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "evento_id")
    )
    private List<Evento> eventos;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "foto_id")
    private Foto foto;

    public void validarUsuario() {
        if (Objects.isNull(this.email) || !EmailHelper.emailValido(this.email)) {
            throw new UsuarioBadRequestException("Email inválido.");
        }
        if (Objects.isNull(this.senha) || !SenhaHelper.senhaSegura(this.senha)) {
            throw new UsuarioBadRequestException("Senha insegura.");
        }
    }

}

