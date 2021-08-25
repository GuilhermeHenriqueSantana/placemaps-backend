package com.esoft.placemaps.placemaps.autenticacao;

import com.esoft.placemaps.placemaps.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/autenticacao")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    @Autowired
    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/cadastrar-usuario")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(this.autenticacaoService.cadastrarUsuario(usuario));
    }

}
