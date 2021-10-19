package com.esoft.placemaps.placemaps.evento;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemana;
import com.esoft.placemaps.placemaps.diadasemana.NomeDiaSemana;
import com.esoft.placemaps.placemaps.evento.exception.EventoBadRequestException;
import com.esoft.placemaps.placemaps.localizacao.Localizacao;
import com.esoft.placemaps.placemaps.localizacao.LocalizacaoRepository;
import com.esoft.placemaps.placemaps.ponto.PontoService;
import com.esoft.placemaps.placemaps.usuario.UsuarioEscopo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    private final LocalizacaoRepository localizacaoRepository;

    private final PontoService pontoService;

    @Autowired
    public EventoService(EventoRepository eventoRepository, LocalizacaoRepository localizacaoRepository, PontoService pontoService) {
        this.eventoRepository = eventoRepository;
        this.localizacaoRepository = localizacaoRepository;
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

    @Transactional(readOnly = true)
    public Evento obterEventoPorId(String id) {
        return this.eventoRepository.findById(id).orElse(null);
    }

    public Evento obterEventoExistente(String eventoId) {
        Optional<Evento> eventoOptional = this.eventoRepository.findById(eventoId);
        if (!eventoOptional.isPresent()) {
            throw new EventoBadRequestException("Evento não encontrado.");
        }
        return eventoOptional.get();
    }

    public Evento obterEventoExistentePelaLocalizacaoId(String localizacaoId) {
        Optional<Evento> eventoOptional = this.eventoRepository.findByLocalizacaoId(localizacaoId);
        if (!eventoOptional.isPresent()) {
            throw new EventoBadRequestException("Evento não encontrado.");
        }
        return eventoOptional.get();
    }

    public List<Evento> obterEventosExistentesPeloPontoId(String pontoId) {  
        return this.eventoRepository.findByPontoId(pontoId);
    }

    private Localizacao copiar(String pontoId, Date data) {
        NomeDiaSemana nomeDiaSemana = new DiaDaSemana().pegarDiaDaSemana(data);
        Localizacao localizacao = this.obterLocalizacaoPeloPontoEDiaDaSemana(pontoId, nomeDiaSemana.toString());
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

    public Localizacao obterLocalizacaoPeloPontoEDiaDaSemana(String pontoId, String nomeDiaSemana) {
        Optional<Localizacao> localizacao = this.localizacaoRepository.obterPorPontoEDiaDaSemana(pontoId, nomeDiaSemana);
        if (!localizacao.isPresent()) {
            throw new EventoBadRequestException("Nenhuma localização encontrada nesse dia da semana para esse ponto.");
        }
        return localizacao.get();
    }

    public Page<Evento> obterEventos(Pageable pageable,
                                     String nome) {
        return this.eventoRepository.findAllByNomeContains(pageable, nome);
    }

    public Page<Map<String, Object>> obterEventosDefinidoComoLembrete(Pageable pageable) {
        return this.eventoRepository.obterEventosDefinidoComoLembrete(pageable, UsuarioEscopo.usuarioAtual().getId());
    }
    
}
