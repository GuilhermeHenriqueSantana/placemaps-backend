package com.esoft.placemaps.placemaps.pedidocadastro;

import java.util.Map;

import com.esoft.placemaps.placemaps.pedidocadastro.dto.AceiteDePedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @PostMapping("/aceitar-pedido")
  public ResponseEntity<String> aceitarPedido(@RequestBody AceiteDePedidoDTO aceiteDePedidoDTO) {
    return ResponseEntity.ok(this.pedidoCadastroService.aceitarPedido(aceiteDePedidoDTO));
  }

  @GetMapping
  public ResponseEntity<Page<PedidoCadastro>> obterPedidosCadastrados(Pageable pageable) {
    return ResponseEntity.ok(this.pedidoCadastroService.obterPedidosCadastrados(pageable));
  }

}
