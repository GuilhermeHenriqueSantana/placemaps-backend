package com.esoft.placemaps.placemaps.opniao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/opiniao")
public class OpiniaoPublicController {

  private final OpiniaoService opiniaoService;

  @Autowired
  public OpiniaoPublicController(OpiniaoService opiniaoService) {
    this.opiniaoService = opiniaoService;
  }

  @GetMapping
  public ResponseEntity<Page<Opiniao>> getPageOrderByDateDesc(Pageable pageable) {
    return ResponseEntity.ok(this.opiniaoService.getPageOrderByDateDesc(pageable));
  }

}