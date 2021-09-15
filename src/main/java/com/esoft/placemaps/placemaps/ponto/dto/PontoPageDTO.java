package com.esoft.placemaps.placemaps.ponto.dto;

import com.esoft.placemaps.placemaps.ponto.projection.PontoPageProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PontoPageDTO {
  private String id;
  private String nome;
  private String foto;
  private List<String> dadoSemanalNomeList;
  private Float nota;

  public PontoPageDTO(PontoPageProjection pontoPageProjection) {
    this.id = pontoPageProjection.getId();
    this.nome = pontoPageProjection.getNome();
    this.foto = pontoPageProjection.getFoto();
    this.nota = pontoPageProjection.getNota();
  }
}
