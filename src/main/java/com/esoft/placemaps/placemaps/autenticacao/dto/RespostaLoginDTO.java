package com.esoft.placemaps.placemaps.autenticacao.dto;

import com.esoft.placemaps.placemaps.usuario.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RespostaLoginDTO {
  private String token;
  private String nome;
  private TipoUsuario tipoUsuario;
  private String foto;
}