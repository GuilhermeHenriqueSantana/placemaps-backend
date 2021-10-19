package com.esoft.placemaps.placemaps.mensagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mensagem")
public class MensagemController {

  private final MensagemService mensagemService;

  @Autowired
  public MensagemController(MensagemService mensagemService) {
    this.mensagemService = mensagemService;
  }

  @GetMapping
  public ResponseEntity<Page<Mensagem>> getPageOrderByDateDesc(Pageable pageable) {
    return ResponseEntity.ok(this.mensagemService.getPageOrderByDateDesc(pageable));
  }

}
