package com.esoft.placemaps.placemaps.ponto;

import com.esoft.placemaps.placemaps.ponto.dto.PontoPageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/ponto")
public class PontoPublicController {

  private final PontoService pontoService;

  @Autowired
  public PontoPublicController(PontoService pontoService) {
    this.pontoService = pontoService;
  }

  @GetMapping
  public ResponseEntity<Page<PontoPageDTO>> pontosPorNomeECategoria(Pageable pageable,
                                                   @RequestParam(value = "categoria", required = false, defaultValue = "") String categoria,
                                                   @RequestParam(value = "nome", required = false, defaultValue = "") String nome) {
    return ResponseEntity.ok(this.pontoService.pontosPorNomeECategoria(pageable, nome, categoria));
  }

}
