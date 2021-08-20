package com.esoft.placemaps.placemaps.pedidocadastro;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.helpers.DocumentoHelper;
import com.esoft.placemaps.helpers.EmailHelper;
import com.esoft.placemaps.helpers.SenhaHelper;
import com.esoft.placemaps.placemaps.pedidocadastro.exception.PedidoCadastroBadRequestException;
import com.esoft.placemaps.placemaps.plano.Plano;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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

    @OneToOne
    @JoinColumn(name = "plano_id", nullable = false)
    private Plano plano;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public void validarPedidoCadastro() {
        if (Objects.isNull(this.numeracaoDocumento) || !DocumentoHelper.documentoValido(this.numeracaoDocumento)) {
            throw new PedidoCadastroBadRequestException("Documento inválido.");
        }
        if (Objects.isNull(this.usuario)) {
            this.validarPedidoCadastroAnonimo();
        }
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
