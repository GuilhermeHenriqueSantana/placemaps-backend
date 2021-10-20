package com.esoft.placemaps.placemaps.avaliacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/public/avaliacao")
public class AvaliacaoPublicController {

  private final AvaliacaoService avaliacaoService;

  @Autowired
  public AvaliacaoPublicController(AvaliacaoService avaliacaoService) {
    this.avaliacaoService = avaliacaoService;
  }


  @GetMapping("/obter-pelo-ponto/{pontoId}")
  public ResponseEntity<Page<Map<String, Object>>> obterAvaliacoesPorPontoId(Pageable pageable,
                                                                              @PathVariable String pontoId) {
    return ResponseEntity.ok(this.avaliacaoService.obterAvaliacoesPeloPonto(pageable, pontoId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Avaliacao> obterAvaliacaoPorId(@PathVariable String id) {
    return ResponseEntity.ok(this.avaliacaoService.obterAvaliacaoPorId(id));
  }
}
