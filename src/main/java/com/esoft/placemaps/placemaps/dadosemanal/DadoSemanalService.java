package com.esoft.placemaps.placemaps.dadosemanal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.esoft.placemaps.placemaps.dadosemanal.dto.DadoSemanalFormDTO;
import com.esoft.placemaps.placemaps.dadosemanal.exception.DadoSemanalBadRequestException;
import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemana;
import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemanaRepository;
import com.esoft.placemaps.placemaps.ponto.Ponto;
import com.esoft.placemaps.placemaps.ponto.PontoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DadoSemanalService {

    private final DadoSemanalRepository dadoSemanalRepository;

    private final PontoRepository pontoRepository;

    private final DiaDaSemanaRepository diaDaSemanaRepository;

    @Autowired
    public DadoSemanalService(DadoSemanalRepository dadoSemanalRepository,
                              PontoRepository pontoRepository,
                              DiaDaSemanaRepository diaDaSemanaRepository) {

        this.dadoSemanalRepository = dadoSemanalRepository;
        this.pontoRepository = pontoRepository;
        this.diaDaSemanaRepository = diaDaSemanaRepository;
    }

    @Transactional
    public DadoSemanal salvar(DadoSemanalFormDTO dadoSemanalFormDTO) {
        DadoSemanal dadoSemanal = dadoSemanalFormDTO.gerarDadoSemanal();
        Optional<Ponto> pontoOptional = pontoRepository.findById(dadoSemanalFormDTO.getPontoId());
        if (!pontoOptional.isPresent()) {
            throw new DadoSemanalBadRequestException("Ponto não encontrado.");
        }
        dadoSemanal.setPonto(pontoOptional.get());
        List<DiaDaSemana> diasDaSemana = new ArrayList<>();
        for (String diaDaSemanaId : dadoSemanalFormDTO.getDiasDaSemanaIds()){
            Optional<DiaDaSemana> diaDaSemanaOptional = diaDaSemanaRepository.findById(diaDaSemanaId);
            if (diaDaSemanaOptional.isPresent()) {
                diasDaSemana.add(diaDaSemanaOptional.get());
            }
        }
        if (diasDaSemana.size() != dadoSemanalFormDTO.getDiasDaSemanaIds().size()) {
            throw new DadoSemanalBadRequestException("Algum(ns) DiaDaSemana não foi encontrado.");
        }
        dadoSemanal.setDiasDaSemana(diasDaSemana);
        return dadoSemanalRepository.save(dadoSemanal);
    }

    @Transactional
    public List<String> obterNomesPorPontoId(String pontoId) {
        return this.dadoSemanalRepository.obterNomesPorPontoId(pontoId, new DiaDaSemana().pegarDiaDaSemana(new Date()).name());
    }

}
