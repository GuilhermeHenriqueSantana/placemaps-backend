package com.esoft.placemaps.placemaps.dadosemanal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class DadoSemanalDiaDaSemanaDTO {
  private String diaDaSemana;
  private List<Map<String, Object>> dadoSemanalList;
}
