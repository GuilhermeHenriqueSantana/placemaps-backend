package com.esoft.placemaps.placemaps.autenticacao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrocaDeSenhaDTO {
  private String email;
  private String senha;
  private String novaSenha;
}