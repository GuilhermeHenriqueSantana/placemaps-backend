package com.esoft.placemaps.placemaps.evento;

import java.util.Optional;

import com.esoft.placemaps.placemaps.evento.exception.EventoBadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    @Autowired
    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public Evento obterEventoExistente(String eventoId) {
        Optional<Evento> eventoOptional = eventoRepository.findById(eventoId);
        if (!eventoOptional.isPresent()) {
            throw new EventoBadRequestException("Evento não econtrado.");
        }
        return eventoOptional.get();
    }
    
}
