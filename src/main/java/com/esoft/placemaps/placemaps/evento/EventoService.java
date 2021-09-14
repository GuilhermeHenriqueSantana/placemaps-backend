package com.esoft.placemaps.placemaps.evento;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemana;
import com.esoft.placemaps.placemaps.diadasemana.NomeDiaSemana;
import com.esoft.placemaps.placemaps.evento.exception.EventoBadRequestException;
import com.esoft.placemaps.placemaps.localizacao.Localizacao;
import com.esoft.placemaps.placemaps.localizacao.LocalizacaoService;
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
            if (Objects.nonNull(evento.getPonto())) {
                throw new EventoBadRequestException("Id do ponto deve ser passado utilizando variavel na url ex: ?pontoId=id");
            } else {
                if (Objects.isNull(evento.getLocalizacao())) {
                    throw new EventoBadRequestException("É necessário informar uma localização.");
                }
            }
        } else {
            evento.setPonto(pontoService.obterPontoExistente(pontoId));
            evento.setLocalizacao(copiar(pontoId, evento.getInicio()));
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

    private Localizacao copiar(String pontoId, Date data) {
        NomeDiaSemana nomeDiaSemana = new DiaDaSemana().pegarDiaDaSemana(data);
        Localizacao localizacao = this.localizacaoService.obterPorPontoEDiaDaSemana(pontoId, nomeDiaSemana.toString());
        return Localizacao.builder()
            .pais(localizacao.getPais())
            .estado(localizacao.getEstado())
            .cidade(localizacao.getCidade())
            .bairro(localizacao.getBairro())
            .numero(localizacao.getNumero())
            .rua(localizacao.getRua())
            .longitude(localizacao.getLongitude())
            .latitude(localizacao.getLatitude())
            .build();
    }
    
}
