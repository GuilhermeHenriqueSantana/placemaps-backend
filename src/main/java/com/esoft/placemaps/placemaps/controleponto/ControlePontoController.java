package com.esoft.placemaps.placemaps.controleponto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/controleponto")
public class ControlePontoController {

    private final ControlePontoService controlePontoService;

    @Autowired
    public ControlePontoController(ControlePontoService controlePontoService) {
        this.controlePontoService = controlePontoService;
    }

    @PutMapping("/solicitar-pontos/{quantidade}")
    public ResponseEntity<String> alterarQuantidadePontosSolicitados(@PathVariable Integer quantidade) {
        return ResponseEntity.ok(this.controlePontoService.alterarQuantidadePontosSolicitados(quantidade));
    }

}
