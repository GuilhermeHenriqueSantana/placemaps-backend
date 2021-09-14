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

  @GetMapping("/{categoria}")
  public ResponseEntity<Page<PontoPageDTO>> salvar(Pageable pageable,
                                                   @PathVariable String categoria,
                                                   @RequestParam(value = "nome") String nome) {
    return ResponseEntity.ok(this.pontoService.pontoPorNomeECategoria(pageable, nome, categoria));
  }

}
