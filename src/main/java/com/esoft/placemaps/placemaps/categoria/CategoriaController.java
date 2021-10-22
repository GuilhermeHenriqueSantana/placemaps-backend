package com.esoft.placemaps.placemaps.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {
    
    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Categoria> salvar(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.salvar(categoria));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity deletarCategoria(@PathVariable String id) {
        this.categoriaService.deletarCategoria(id);
        return ResponseEntity.accepted().build();
    }

}
