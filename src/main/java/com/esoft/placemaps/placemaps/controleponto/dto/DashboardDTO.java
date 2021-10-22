package com.esoft.placemaps.placemaps.controleponto.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DashboardDTO {

  private Integer qtdePontosAtivos;
  private Integer qtdePontosPermitidos;
  private Integer qtdeComentarios;
  private Integer qtdeAvaliacoes;

}
