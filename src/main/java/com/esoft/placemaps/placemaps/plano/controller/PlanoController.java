package com.esoft.placemaps.placemaps.plano.controller;

import com.esoft.placemaps.placemaps.plano.Plano;
import com.esoft.placemaps.placemaps.plano.service.PlanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plano")
public class PlanoController {
    private final PlanoService planoService;

    @Autowired
    public PlanoController(PlanoService planoService) {
        this.planoService = planoService;
    }

    @PostMapping
    public ResponseEntity<Plano> save(@RequestBody Plano plano) {
        return ResponseEntity.ok(this.planoService.save(plano));
    }
}
