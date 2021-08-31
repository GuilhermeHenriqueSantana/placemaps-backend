package com.esoft.placemaps.placemaps.ponto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ponto")
public class PontoController {
    
    private final PontoService pontoService;

    @Autowired
    public PontoController(PontoService pontoService) {
        this.pontoService = pontoService;
    }

    @PostMapping("/categoria/{categoriaId}")
    public ResponseEntity<Ponto> salvar(@PathVariable String categoriaId, @RequestBody Ponto ponto) {
        return ResponseEntity.ok(this.pontoService.salvar(categoriaId, ponto));
    }

}
