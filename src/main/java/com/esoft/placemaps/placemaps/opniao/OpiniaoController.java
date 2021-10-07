package com.esoft.placemaps.placemaps.opniao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/opiniao")
public class OpiniaoController {

    private final OpiniaoService opiniaoService;

    @Autowired
    public OpiniaoController(OpiniaoService opiniaoService) {
        this.opiniaoService = opiniaoService;
    }

    @PostMapping
    public ResponseEntity<Opiniao> salvar(@RequestBody String descricao) {
        return ResponseEntity.ok(this.opiniaoService.salvar(descricao));
    }

}
