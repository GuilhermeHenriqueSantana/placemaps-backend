package com.esoft.placemaps.placemaps.pedidocadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/pedidocadastro")
public class PedidoCadastroPublicController {
    private final PedidoCadastroService pedidoCadastroService;

    @Autowired
    public PedidoCadastroPublicController(PedidoCadastroService pedidoCadastroService) {
        this.pedidoCadastroService = pedidoCadastroService;
    }

    @PostMapping
    public ResponseEntity<PedidoCadastro> save(@RequestBody PedidoCadastro pedidoCadastro) {
        return ResponseEntity.ok(this.pedidoCadastroService.save(pedidoCadastro));
    }
}
