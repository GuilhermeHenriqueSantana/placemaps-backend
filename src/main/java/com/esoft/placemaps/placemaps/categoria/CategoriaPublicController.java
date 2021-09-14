package com.esoft.placemaps.placemaps.categoria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/categoria")
public class CategoriaPublicController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaPublicController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> obterTodas() {
        return ResponseEntity.ok(categoriaService.obeterTodas());
    }
    
}
