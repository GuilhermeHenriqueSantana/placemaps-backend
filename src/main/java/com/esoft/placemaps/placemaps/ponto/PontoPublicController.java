package com.esoft.placemaps.placemaps.ponto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/public/ponto")
public class PontoPublicController {

  private final PontoService pontoService;

  @Autowired
  public PontoPublicController(PontoService pontoService) {
    this.pontoService = pontoService;
  }

  @GetMapping
  public ResponseEntity<Page<Map<String, Object>>> pontosPorNomeECategoria(Pageable pageable,
                                                                           @RequestParam(value = "categoria", required = false, defaultValue = "") String categoria,
                                                                           @RequestParam(value = "nome", required = false, defaultValue = "") String nome) {
    return ResponseEntity.ok(this.pontoService.pontosPorNomeECategoria(pageable, nome, categoria));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Ponto> obterPontoExistente(@PathVariable String id) {
    return ResponseEntity.ok(this.pontoService.obterPontoExistente(id));
  }

}
