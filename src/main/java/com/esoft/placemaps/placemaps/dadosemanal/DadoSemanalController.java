package com.esoft.placemaps.placemaps.dadosemanal;

import com.esoft.placemaps.placemaps.dadosemanal.dto.DadoSemanalAtualizarDTO;
import com.esoft.placemaps.placemaps.dadosemanal.dto.DadoSemanalFormDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @PostMapping
    public ResponseEntity<DadoSemanal> salvar(@RequestBody DadoSemanalFormDTO dadoSemanalFormDTO) {
        return ResponseEntity.ok(this.dadoSemanalService.salvar(dadoSemanalFormDTO));
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @PutMapping("/{id}")
    public ResponseEntity<DadoSemanal> atualizar(@PathVariable String id, @RequestBody DadoSemanalAtualizarDTO dadoSemanalAtualizarDTO) {
        return ResponseEntity.ok(this.dadoSemanalService.atualizar(id, dadoSemanalAtualizarDTO));
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity deletarDadoSemanal(@PathVariable String id) {
        this.dadoSemanalService.deletarDadoSemanal(id);
        return ResponseEntity.accepted().build();
    }

}
