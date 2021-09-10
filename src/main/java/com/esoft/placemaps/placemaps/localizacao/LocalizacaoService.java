package com.esoft.placemaps.placemaps.localizacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemana;
import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemanaRepository;
import com.esoft.placemaps.placemaps.localizacao.dto.LocalizacaoFormDTO;
import com.esoft.placemaps.placemaps.localizacao.exception.LocalizacaoBadRequestException;
import com.esoft.placemaps.placemaps.ponto.PontoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalizacaoService {

    private final LocalizacaoRepository localizacaoRepository;

    private final PontoService pontoService;

    private final DiaDaSemanaRepository diaDaSemanaRepository;

    @Autowired
    public LocalizacaoService(LocalizacaoRepository localizacaoRepository,
                              PontoService pontoService,
                              DiaDaSemanaRepository diaDaSemanaRepository) {

        this.localizacaoRepository = localizacaoRepository;
        this.pontoService = pontoService;
        this.diaDaSemanaRepository = diaDaSemanaRepository;
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
    public Localizacao obterPorPontoEDiaDaSemana(String pontoId, String nomeDiaSemana) {
        Optional<Localizacao> localizacao = this.localizacaoRepository.obterPorPontoEDiaDaSemana(pontoId, nomeDiaSemana);
        if (!localizacao.isPresent()) {
            throw new LocalizacaoBadRequestException("Nenhuma localização encontrada nesse dia da semana para esse ponto.");
        }
        return localizacao.get();
    }
    
}
