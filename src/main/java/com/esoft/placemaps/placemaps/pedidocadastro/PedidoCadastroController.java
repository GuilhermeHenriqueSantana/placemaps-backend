package com.esoft.placemaps.placemaps.pedidocadastro;

import com.esoft.placemaps.placemaps.pedidocadastro.dto.AceiteDePedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidocadastro")
public class PedidoCadastroController {

  private final PedidoCadastroService pedidoCadastroService;

  @Autowired
  public PedidoCadastroController(PedidoCadastroService pedidoCadastroService) {
    this.pedidoCadastroService = pedidoCadastroService;
  }

  @PostMapping("/aceitar-pedido")
  public ResponseEntity<String> aceitarPedido(@RequestBody AceiteDePedidoDTO aceiteDePedidoDTO) {
    return ResponseEntity.ok(this.pedidoCadastroService.aceitarPedido(aceiteDePedidoDTO));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PedidoCadastro> obterPedidoPeloId(@PathVariable String id) {
    return ResponseEntity.ok(this.pedidoCadastroService.obterPedidoPeloId(id));
  }
}
