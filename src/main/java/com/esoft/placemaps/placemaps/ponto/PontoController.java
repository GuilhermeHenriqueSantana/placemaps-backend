package com.esoft.placemaps.placemaps.ponto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ponto")
public class PontoController {
    
    private final PontoService pontoService;

    @Autowired
    public PontoController(PontoService pontoService) {
        this.pontoService = pontoService;
    }

    @PostMapping("/categoria/{categoriaId}")
    public ResponseEntity<String> salvar(@PathVariable String categoriaId, @RequestBody Ponto ponto) {
        return ResponseEntity.ok(this.pontoService.salvar(categoriaId, ponto));
    }

    @PutMapping("/ativar/{pontoId}")
    public ResponseEntity<String> ativar(@PathVariable String pontoId) {
        return ResponseEntity.ok(this.pontoService.ativarDesativar(pontoId, true));
    }

    @PutMapping("/desativar/{pontoId}")
    public ResponseEntity<String> desativar(@PathVariable String pontoId) {
        return ResponseEntity.ok(this.pontoService.ativarDesativar(pontoId, false));
    }

    @GetMapping("/obter-pelo-proprietario")
    public ResponseEntity<Page<Map<String, Object>>> pontosPorNomeCategoriaProprietario(Pageable pageable,
                                                                             @RequestParam(value = "categoria", required = false, defaultValue = "") String categoria,
                                                                             @RequestParam(value = "nome", required = false, defaultValue = "") String nome) {
        return ResponseEntity.ok(this.pontoService.pontosPorNomeCategoriaProprietario(pageable, nome, categoria));
    }

}
