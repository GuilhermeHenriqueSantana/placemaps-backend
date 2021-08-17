package com.esoft.placemaps.placemaps.pedidocadastro;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.helpers.DocumentoHelper;
import com.esoft.placemaps.helpers.EmailHelper;
import com.esoft.placemaps.helpers.SenhaHelper;
import com.esoft.placemaps.placemaps.pedidocadastro.exception.PedidoCadastroBadRequestException;
import com.esoft.placemaps.placemaps.plano.Plano;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PedidoCadastro extends BasicClass {
    @NotNull
    private String nome;
    @NotNull
    private String email;
    @NotNull
    private String senha;
    @NotNull
    private String numeracaoDocumento;
    @NotNull
    @OneToOne
    private Plano plano;

    //@OneToOne --- Fazer ligação com usuario
    private String usuario;

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
