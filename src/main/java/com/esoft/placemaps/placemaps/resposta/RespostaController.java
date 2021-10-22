package com.esoft.placemaps.placemaps.resposta;

import com.esoft.placemaps.placemaps.resposta.dto.RespostaFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resposta")
public class RespostaController {

    private final RespostaService respostaService;

    @Autowired
    public RespostaController(RespostaService respostaService) {
        this.respostaService = respostaService;
    }

    @PreAuthorize("hasRole('PROPRIETARIO') or hasRole('USUARIO')")
    @PostMapping
    public ResponseEntity<Resposta> salvar(@RequestBody RespostaFormDTO respostaFormDTO) {
        return ResponseEntity.ok(this.respostaService.salvar(respostaFormDTO));
    }

}
