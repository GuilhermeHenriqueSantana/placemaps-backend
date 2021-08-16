package com.esoft.placemaps.pedidocadastro.controller;

import com.esoft.placemaps.pedidocadastro.PedidoCadastro;
import com.esoft.placemaps.pedidocadastro.service.PedidoCadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidocadastro")
public class PedidoCadastroController {
    private final PedidoCadastroService pedidoCadastroService;

    @Autowired
    public PedidoCadastroController(PedidoCadastroService pedidoCadastroService) {
        this.pedidoCadastroService = pedidoCadastroService;
    }

    @PostMapping
    public ResponseEntity<PedidoCadastro> save(@RequestBody PedidoCadastro pedidoCadastro) {
        return ResponseEntity.ok(this.pedidoCadastroService.save(pedidoCadastro));
    }
}
