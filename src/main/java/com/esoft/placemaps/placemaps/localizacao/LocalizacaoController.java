package com.esoft.placemaps.placemaps.localizacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/localizacao")
public class LocalizacaoController {
    
    @Autowired
    private LocalizacaoService localizacaoService;

    @PostMapping
    public ResponseEntity<Localizacao> save(@RequestBody Localizacao localizacao) {
        return ResponseEntity.ok(this.localizacaoService.save(localizacao));
    }

}
