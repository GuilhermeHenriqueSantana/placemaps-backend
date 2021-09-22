package com.esoft.placemaps.placemaps.comentario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/public/comentario")
public class ComentarioPublicController {

  private final ComentarioService comentarioService;

  public ComentarioPublicController(ComentarioService comentarioService) {
    this.comentarioService = comentarioService;
  }

  @GetMapping("/obter-pelo-ponto/{pontoId}")
  public ResponseEntity<Page<Map<String, Object>>> obterComentariosPorPontoId(Pageable pageable,
                                                                        @PathVariable String pontoId) {
    return ResponseEntity.ok(this.comentarioService.obterComentariosPorPontoId(pageable, pontoId));
  }
}
