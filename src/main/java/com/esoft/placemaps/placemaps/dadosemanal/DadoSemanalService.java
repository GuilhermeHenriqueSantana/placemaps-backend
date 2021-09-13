package com.esoft.placemaps.placemaps.dadosemanal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.esoft.placemaps.placemaps.dadosemanal.dto.DadoSemanalFormDTO;
import com.esoft.placemaps.placemaps.dadosemanal.exception.DadoSemanalBadRequestException;
import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemana;
import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemanaRepository;
import com.esoft.placemaps.placemaps.ponto.PontoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DadoSemanalService {

    private final DadoSemanalRepository dadoSemanalRepository;

    private final PontoService pontoService;

    private final DiaDaSemanaRepository diaDaSemanaRepository;

    @Autowired
    public DadoSemanalService(DadoSemanalRepository dadoSemanalRepository,
                              PontoService pontoService,
                              DiaDaSemanaRepository diaDaSemanaRepository) {

        this.dadoSemanalRepository = dadoSemanalRepository;
        this.pontoService = pontoService;
        this.diaDaSemanaRepository = diaDaSemanaRepository;                  
    }

    @Transactional
    public DadoSemanal salvar(DadoSemanalFormDTO dadoSemanalFormDTO) {
        DadoSemanal dadoSemanal = dadoSemanalFormDTO.gerarDadoSemanal();
        dadoSemanal.setPonto(this.pontoService.obterPontoExistente(dadoSemanalFormDTO.getPontoId()));
        List<DiaDaSemana> diasDaSemana = this.diaDaSemanaRepository.obterDiasDaSemanaPorIds(dadoSemanalFormDTO.getDiasDaSemanaIds());
        if (diasDaSemana.size() != dadoSemanalFormDTO.getDiasDaSemanaIds().size()) {
            throw new DadoSemanalBadRequestException("Algum(ns) DiaDaSemana não foi encontrado.");
        }
        dadoSemanal.setDiasDaSemana(diasDaSemana);
        return dadoSemanalRepository.save(dadoSemanal);
    }
    
}
