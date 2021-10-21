package com.esoft.placemaps.placemaps.localizacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/public/localizacao")
public class LocalizacaoPublicController {

  private final LocalizacaoService localizacaoService;

  @Autowired
  public LocalizacaoPublicController(LocalizacaoService localizacaoService) {
    this.localizacaoService = localizacaoService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Localizacao> obterLocalizacaoPeloId(@PathVariable String id) {
    return ResponseEntity.ok(this.localizacaoService.obterLocalizacaoPeloId(id));
  }

  @GetMapping("/ponto/{pontoId}")
  public ResponseEntity<Page<Map<String, Object>>> obterLocalizacoesPeloPontoId(Pageable pageable,
                                                                                @PathVariable String pontoId) {
    return ResponseEntity.ok(this.localizacaoService.obterLocalizacoesPeloPontoId(pageable, pontoId));
  }
}
