package com.esoft.placemaps.placemaps.pedidocadastro;

import com.esoft.placemaps.placemaps.pedidocadastro.dto.AceiteDePedidoDTO;
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
    public ResponseEntity<PedidoCadastro> salvar(@RequestBody PedidoCadastro pedidoCadastro) {
        return ResponseEntity.ok(this.pedidoCadastroService.salvar(pedidoCadastro));
    }

    @PostMapping("/aceitar-pedido")
    public ResponseEntity<String> aceitarPedido(@RequestBody AceiteDePedidoDTO aceiteDePedidoDTO) {
        return ResponseEntity.ok(this.pedidoCadastroService.aceitarPedido(aceiteDePedidoDTO));
    }

}
