package com.esoft.placemaps.placemaps.evento;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evento")
public class EventoController {

    private final EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @PostMapping
    public ResponseEntity<Evento> salvar(@RequestParam(required = false, defaultValue = "") String pontoId, @RequestBody Evento evento) {
       return ResponseEntity.ok(this.eventoService.salvar(pontoId, evento));
    }

    @PreAuthorize("hasRole('PROPRIETARIO') or hasRole('USUARIO')")
    @GetMapping("/lembretes")
    public ResponseEntity<Page<Map<String, Object>>> obterEventosDefinidoComoLembrete(Pageable pageable) {
        return ResponseEntity.ok(eventoService.obterEventosDefinidoComoLembrete(pageable));
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @PutMapping("/ativar/{id}")
    public ResponseEntity ativar(@PathVariable String id) {
        this.eventoService.ativarDesativar(id, Boolean.TRUE);
        return ResponseEntity.accepted().build();
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @PutMapping("/desativar/{id}")
    public ResponseEntity desativar(@PathVariable String id) {
        this.eventoService.ativarDesativar(id, Boolean.FALSE);
        return ResponseEntity.accepted().build();
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @GetMapping("/proprietario")
    public ResponseEntity<Page<Map<String, Object>>> obterEventosPeloProprietario(Pageable pageable) {
        return ResponseEntity.ok(this.eventoService.obterEventosPeloProprietario(pageable));
    }
    
}
