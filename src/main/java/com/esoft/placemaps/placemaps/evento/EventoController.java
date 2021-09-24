package com.esoft.placemaps.placemaps.evento;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/evento")
public class EventoController {

    private final EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    public ResponseEntity<Evento> salvar(@RequestParam(required = false, defaultValue = "") String pontoId, @RequestBody Evento evento) {
       return ResponseEntity.ok(this.eventoService.salvar(pontoId, evento));
    }

    @GetMapping("/lembretes")
    public ResponseEntity<Page<Map<String, Object>>> obterEventosDefinidoComoLembrete(Pageable pageable) {
        return ResponseEntity.ok(eventoService.obterEventosDefinidoComoLembrete(pageable));
    }
    
}
