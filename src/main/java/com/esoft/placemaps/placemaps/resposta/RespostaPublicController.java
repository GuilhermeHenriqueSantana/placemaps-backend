package com.esoft.placemaps.placemaps.resposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/public/resposta")
public class RespostaPublicController {

  private final RespostaService respostaService;

  @Autowired
  public RespostaPublicController(RespostaService respostaService) {
    this.respostaService = respostaService;
  }

  @GetMapping("/obter-pelo-comentario/{comentarioId}")
  public ResponseEntity<Page<Map<String, Object>>> obterRespostasPeloComentarioId(Pageable pageable,
                                                                              @PathVariable String comentarioId) {
    return ResponseEntity.ok(this.respostaService.obterRespostasPeloComentarioId(pageable, comentarioId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Resposta> obterRespostaPeloId(@PathVariable String id) {
    return ResponseEntity.ok(this.respostaService.obterRespostaPeloId(id));
  }

}
