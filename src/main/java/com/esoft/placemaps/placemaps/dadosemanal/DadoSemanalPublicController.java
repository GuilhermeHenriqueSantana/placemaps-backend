package com.esoft.placemaps.placemaps.dadosemanal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/dadosemanal")
public class DadoSemanalPublicController {

  private final DadoSemanalService dadoSemanalService;

  @Autowired
  public DadoSemanalPublicController(DadoSemanalService dadoSemanalService) {
    this.dadoSemanalService = dadoSemanalService;
  }

  @GetMapping("/obter-pelo-ponto/{pontoId}")
  public ResponseEntity<Page<DadoSemanal>> obterDadosPorPontoId(Pageable pageable,
                                                                @PathVariable String pontoId) {
    return ResponseEntity.ok(this.dadoSemanalService.obterDadosPorPontoId(pageable, pontoId));
  }

}
