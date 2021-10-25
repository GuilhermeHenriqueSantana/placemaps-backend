package com.esoft.placemaps.placemaps.avaliacao;

import com.esoft.placemaps.placemaps.avaliacao.dto.AvaliacaoFormDTO;
import com.esoft.placemaps.placemaps.avaliacao.dto.RespostaDeAvaliacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/avaliacao")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @Autowired
    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PreAuthorize("hasRole('PROPRIETARIO') or hasRole('USUARIO')")
    @PostMapping
    public ResponseEntity<Avaliacao> salvar(@RequestBody AvaliacaoFormDTO avaliacaoFormDTO) {
        return ResponseEntity.ok(this.avaliacaoService.salvar(avaliacaoFormDTO));
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @PutMapping(path = "/responder-avaliacao")
    public ResponseEntity<Avaliacao> responderAvaliacao(@RequestBody RespostaDeAvaliacaoDTO respostaDeAvaliacaoDTO) {
        return ResponseEntity.ok(this.avaliacaoService.responderAvaliacao(respostaDeAvaliacaoDTO));
    }
}
