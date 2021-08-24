package com.esoft.placemaps.placemaps.dadosemanal;

import com.esoft.placemaps.placemaps.dadosemanal.dto.DadoSemanalFormDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dadosemanal")
public class DadoSemanalController {
    
    private final DadoSemanalService dadoSemanalService;

    @Autowired
    public DadoSemanalController(DadoSemanalService dadoSemanalService) {
        this.dadoSemanalService = dadoSemanalService;
    }

    @PostMapping
    public ResponseEntity<DadoSemanal> salvar(@RequestBody DadoSemanalFormDTO dadoSemanalFormDTO) {
        return ResponseEntity.ok(this.dadoSemanalService.salvar(dadoSemanalFormDTO));
    }

}
