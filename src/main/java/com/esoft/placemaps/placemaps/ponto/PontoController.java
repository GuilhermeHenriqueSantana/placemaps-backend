package com.esoft.placemaps.placemaps.ponto;

import java.util.List;

import com.esoft.placemaps.placemaps.localizacao.LocalizacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ponto")
public class PontoController {
    
    @Autowired
    private PontoService pontoService;

    @Autowired
    private LocalizacaoService localizacaoService;

    // utilizar dto
    @PostMapping
    public ResponseEntity<Ponto> save(@RequestBody Ponto ponto, @RequestBody List<String> localizacaoIds) {
        ponto.setLocalizacoes(localizacaoService.pegarLocalizacoesPeloId(localizacaoIds));
        return ResponseEntity.ok(this.pontoService.save(ponto));
    }

}
