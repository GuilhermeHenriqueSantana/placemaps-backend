package com.esoft.placemaps.placemaps.pedidocadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/pedidocadastro")
public class PedidoCadastroPublicController {

    private final PedidoCadastroService pedidoCadastroService;

    @Autowired
    public PedidoCadastroPublicController(PedidoCadastroService pedidoCadastroService) {
        this.pedidoCadastroService = pedidoCadastroService;
    }

    @PostMapping
    public ResponseEntity<PedidoCadastro> salvar(@RequestBody PedidoCadastro pedidoCadastro,
                                                 @RequestParam(name = "planoId") String planoId) {
        return ResponseEntity.ok(this.pedidoCadastroService.salvar(pedidoCadastro, planoId));
    }

}
