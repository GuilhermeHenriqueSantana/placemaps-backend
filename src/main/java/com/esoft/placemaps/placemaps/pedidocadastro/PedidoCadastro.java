package com.esoft.placemaps.placemaps.pedidocadastro;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.helpers.DocumentoHelper;
import com.esoft.placemaps.helpers.EmailHelper;
import com.esoft.placemaps.helpers.SenhaHelper;
import com.esoft.placemaps.placemaps.pedidocadastro.exception.PedidoCadastroBadRequestException;
import com.esoft.placemaps.placemaps.plano.Plano;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import com.esoft.placemaps.placemaps.usuario.UsuarioEscopo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PedidoCadastro extends BasicClass {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "numeracao_documento", nullable = false, length = 14)
    private String numeracaoDocumento;

    @Column(name = "data", nullable = false)  
    private Date data;

    @ManyToOne
    @JoinColumn(name = "plano_id", nullable = false)
    private Plano plano;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public PedidoCadastro validarPedidoCadastro() {
        if (Objects.isNull(this.numeracaoDocumento) || !DocumentoHelper.documentoValido(this.numeracaoDocumento)) {
            throw new PedidoCadastroBadRequestException("Documento inválido.");
        }
        if (Objects.isNull(UsuarioEscopo.usuarioAtual())) {
            this.validarPedidoCadastroAnonimo();
        } else {
            this.usuario = UsuarioEscopo.usuarioAtual();
            this.senha = this.usuario.getSenha();
            this.email = this.usuario.getEmail();
            this.nome = this.usuario.getNome();
        }
        return this;
    }

    public void validarPedidoCadastroAnonimo() {
        if (Objects.isNull(this.email) || !EmailHelper.emailValido(this.email)) {
            throw new PedidoCadastroBadRequestException("Email inválido.");
        }
        if (Objects.isNull(this.senha) || !SenhaHelper.senhaSegura(this.senha)) {
            throw new PedidoCadastroBadRequestException("Senha insegura.");
        }
    }

}
