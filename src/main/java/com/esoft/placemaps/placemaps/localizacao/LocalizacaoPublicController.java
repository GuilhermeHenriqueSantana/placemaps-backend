package com.esoft.placemaps.placemaps.localizacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    return ResponseEntity.ok(this.localizacaoService.obterLocalizacaoExistente(id));
  }

}
