package com.esoft.placemaps.placemaps.controleponto;

import com.esoft.placemaps.placemaps.controleponto.dto.DashboardDTO;
import com.esoft.placemaps.placemaps.ponto.PontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/controleponto")
public class ControlePontoController {

    private final ControlePontoService controlePontoService;

    @Autowired
    public ControlePontoController(ControlePontoService controlePontoService) {
        this.controlePontoService = controlePontoService;
    }

    @PutMapping("/solicitar-pontos/{quantidade}")
    public ResponseEntity<String> alterarQuantidadePontosSolicitados(@PathVariable Integer quantidade) {
        return ResponseEntity.ok(this.controlePontoService.alterarQuantidadePontosSolicitados(quantidade));
    }

    @PutMapping("/aceitar-solicitacao-pontos/{controlePontoId}")
    public ResponseEntity<String> aceitarSolicitacaoDeAlteracao(@PathVariable String controlePontoId) {
        return ResponseEntity.ok(this.controlePontoService.aceitarNegarSolicitacaoDeAlteracao(controlePontoId, true));
    }

    @PutMapping("/negar-solicitacao-pontos/{controlePontoId}")
    public ResponseEntity<String> negarSolicitacaoDeAlteracao(@PathVariable String controlePontoId) {
        return ResponseEntity.ok(this.controlePontoService.aceitarNegarSolicitacaoDeAlteracao(controlePontoId, false));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> obterPeloProprietario() {
        return ResponseEntity.ok(this.controlePontoService.obterPeloProprietario());
    }

    @GetMapping("/solicitados")
    public ResponseEntity<Page<Map<String, Object>>> obterControlesPontoSolicitados(Pageable pageable) {
        return ResponseEntity.ok(this.controlePontoService.obterControlesPontoSolicitados(pageable));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> dashboard() {
        return ResponseEntity.ok(this.controlePontoService.dashboard());
    }

}
