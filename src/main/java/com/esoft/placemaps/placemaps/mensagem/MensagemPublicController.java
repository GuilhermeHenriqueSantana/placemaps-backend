package com.esoft.placemaps.placemaps.mensagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/mensagem")
public class MensagemPublicController {

    private final MensagemService mensagemService;

    @Autowired
    public MensagemPublicController(MensagemService mensagemService) {
        this.mensagemService = mensagemService;
    }

    @PostMapping
    public ResponseEntity<Mensagem> salvar(@RequestBody Mensagem mensagem) {
        return ResponseEntity.ok(this.mensagemService.salvar(mensagem));
    }

}
