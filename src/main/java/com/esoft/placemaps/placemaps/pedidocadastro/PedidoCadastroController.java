package com.esoft.placemaps.placemaps.pedidocadastro;

import java.util.Map;

import com.esoft.placemaps.placemaps.pedidocadastro.dto.AceiteDePedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/pedidocadastro")
public class PedidoCadastroController {

  private final PedidoCadastroService pedidoCadastroService;

  @Autowired
  public PedidoCadastroController(PedidoCadastroService pedidoCadastroService) {
    this.pedidoCadastroService = pedidoCadastroService;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/aceitar-pedido")
  public ResponseEntity aceitarPedido(@RequestBody AceiteDePedidoDTO aceiteDePedidoDTO) {
    this.pedidoCadastroService.aceitarPedido(aceiteDePedidoDTO);
    return ResponseEntity.accepted().build();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/negar-pedido/{id}")
  public ResponseEntity<String> negarPedido(@PathVariable String id) {
    this.pedidoCadastroService.negarPedido(id);
    return ResponseEntity.accepted().build();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<PedidoCadastro> obterPedidoPeloId(@PathVariable String id) {
    return ResponseEntity.ok(this.pedidoCadastroService.obterPedidoPeloId(id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<Page<PedidoCadastro>> obterPedidosCadastrados(Pageable pageable) {
    return ResponseEntity.ok(this.pedidoCadastroService.obterPedidosCadastrados(pageable));
  }

}
