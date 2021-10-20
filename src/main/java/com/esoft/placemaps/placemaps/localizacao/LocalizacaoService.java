package com.esoft.placemaps.placemaps.localizacao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemana;
import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemanaRepository;
import com.esoft.placemaps.placemaps.evento.Evento;
import com.esoft.placemaps.placemaps.evento.EventoService;
import com.esoft.placemaps.placemaps.localizacao.dto.LocalizacaoAtualizarDTO;
import com.esoft.placemaps.placemaps.localizacao.dto.LocalizacaoFormDTO;
import com.esoft.placemaps.placemaps.localizacao.exception.LocalizacaoBadRequestException;
import com.esoft.placemaps.placemaps.ponto.PontoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalizacaoService {

    private final LocalizacaoRepository localizacaoRepository;

    private final PontoService pontoService;

    private final DiaDaSemanaRepository diaDaSemanaRepository;

    private final EventoService eventoService;

    @Autowired
    public LocalizacaoService(LocalizacaoRepository localizacaoRepository,
                              PontoService pontoService,
                              DiaDaSemanaRepository diaDaSemanaRepository,
                              EventoService eventoService) {

        this.localizacaoRepository = localizacaoRepository;
        this.pontoService = pontoService;
        this.diaDaSemanaRepository = diaDaSemanaRepository;
        this.eventoService = eventoService;
    }

    @Transactional
    public Localizacao salvar(LocalizacaoFormDTO localizacaoFormDTO) {
        Localizacao localizacao = localizacaoFormDTO.gerarLocalizacao();
        localizacao.setPonto(this.pontoService.obterPontoExistente(localizacaoFormDTO.getPontoId()));
        List<DiaDaSemana> diasDaSemana = this.diaDaSemanaRepository.obterDiasDaSemanaPorIds(localizacaoFormDTO.getDiasDaSemanaIds());
        if (diasDaSemana.size() != localizacaoFormDTO.getDiasDaSemanaIds().size()) {
            throw new LocalizacaoBadRequestException("Algum(ns) DiaDaSemana não foi encontrado.");
        }
        localizacao.setDiasDaSemana(diasDaSemana);
        return localizacaoRepository.save(localizacao);
    }

    @Transactional
    public Localizacao atualizar(String id, LocalizacaoAtualizarDTO localizacaoAtualizarDTO) {
        Localizacao localizacao = localizacaoAtualizarDTO.atualizarLocalizacao(this.obterLocalizacaoExistente(id));
        List<DiaDaSemana> diasDaSemana = this.diaDaSemanaRepository.obterDiasDaSemanaPorIds(localizacaoAtualizarDTO.getDiasDaSemanaIds());
        if (diasDaSemana.size() != localizacaoAtualizarDTO.getDiasDaSemanaIds().size()) {
            throw new LocalizacaoBadRequestException("Algum(ns) DiaDaSemana não foi encontrado.");
        }
        localizacao.setDiasDaSemana(diasDaSemana);
        if (Objects.isNull(localizacao.getPonto())) {
            return this.atualizarComEvento(localizacao);
        } else {
            return this.atualizarComPonto(localizacao, localizacaoAtualizarDTO);
        }
    }

    public Localizacao atualizarComEvento(Localizacao localizacao) {
        Evento evento = this.eventoService.obterEventoExistentePelaLocalizacaoId(localizacao.getId());
        if (Objects.nonNull(evento.getPonto())) {
            throw new LocalizacaoBadRequestException("Para atualizar a localização desse evento, se deve atualizar utilizando a localização do ponto");
        }
        return this.localizacaoRepository.save(localizacao);
    }

    public Localizacao atualizarComPonto(Localizacao localizacao, LocalizacaoAtualizarDTO localizacaoAtualizarDTO) {
        List<Evento> eventos = this.eventoService.obterEventosExistentesPeloPontoId(localizacao.getPonto().getId());
        if (eventos.size() > 0) {
            for (Evento evento : eventos) {
                Localizacao localizacaoEvento = localizacaoAtualizarDTO.atualizarLocalizacao(evento.getLocalizacao());
                localizacaoEvento.getDiasDaSemana().clear();
                localizacaoEvento.getDiasDaSemana().addAll(localizacao.getDiasDaSemana());
                this.localizacaoRepository.save(localizacaoEvento);
            }
        }
        return this.localizacaoRepository.save(localizacao);
    }

    public Localizacao obterLocalizacaoExistente(String localizacaoId) {
        Optional<Localizacao> localizacaoOptional = localizacaoRepository.findById(localizacaoId);
        if (!localizacaoOptional.isPresent()) {
            throw new LocalizacaoBadRequestException("localizacao não econtrada.");
        }
        return localizacaoOptional.get();
    }

    public Localizacao obterLocalizacaoPeloId(String id) {
        return this.localizacaoRepository.findById(id).orElse(null);
    }
}
