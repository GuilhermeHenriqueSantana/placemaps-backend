package com.esoft.placemaps.placemaps.evento;

import java.util.Optional;

import javax.transaction.Transactional;

import com.esoft.placemaps.placemaps.evento.exception.EventoBadRequestException;
import com.esoft.placemaps.placemaps.localizacao.Localizacao;
import com.esoft.placemaps.placemaps.localizacao.LocalizacaoService;
import com.esoft.placemaps.placemaps.ponto.Ponto;
import com.esoft.placemaps.placemaps.ponto.PontoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    private final LocalizacaoService localizacaoService;

    private final PontoService pontoService;

    @Autowired
    public EventoService(EventoRepository eventoRepository, LocalizacaoService localizacaoService, PontoService pontoService) {
        this.eventoRepository = eventoRepository;
        this.localizacaoService = localizacaoService;
        this.pontoService = pontoService;
    }

    @Transactional
    public Evento salvar(String pontoId, Evento evento) {
        if (pontoId.isEmpty()) {
            if (evento.getPonto() != null) {
                throw new EventoBadRequestException("Id do ponto deve ser passado utilizando variavel na url ex: ?pontoId=id");
            }
        } else {
            Ponto ponto = pontoService.obterPontoExistente(pontoId);
            Localizacao localizacao = localizacaoService.obterLocalizacaoPeloIdPonto(pontoId);
            evento.setPonto(ponto);
            evento.setLocalizacao(localizacao);
        }
        return this.eventoRepository.save(evento);
    }

    public Evento obterEventoExistente(String eventoId) {
        Optional<Evento> eventoOptional = eventoRepository.findById(eventoId);
        if (!eventoOptional.isPresent()) {
            throw new EventoBadRequestException("Evento não econtrado.");
        }
        return eventoOptional.get();
    }
    
}
