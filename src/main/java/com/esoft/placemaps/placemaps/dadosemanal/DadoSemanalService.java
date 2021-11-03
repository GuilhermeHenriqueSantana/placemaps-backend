package com.esoft.placemaps.placemaps.dadosemanal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.esoft.placemaps.placemaps.dadosemanal.dto.DadoSemanalAtualizarDTO;
import com.esoft.placemaps.placemaps.dadosemanal.dto.DadoSemanalDiaDaSemanaDTO;
import com.esoft.placemaps.placemaps.dadosemanal.dto.DadoSemanalFormDTO;
import com.esoft.placemaps.placemaps.dadosemanal.exception.DadoSemanalBadRequestException;
import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemana;
import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemanaRepository;
import com.esoft.placemaps.placemaps.item.ItemService;
import com.esoft.placemaps.placemaps.ponto.PontoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DadoSemanalService {

    private final DadoSemanalRepository dadoSemanalRepository;

    private final PontoService pontoService;

    private final DiaDaSemanaRepository diaDaSemanaRepository;

    private final ItemService itemService;

    @Autowired
    public DadoSemanalService(DadoSemanalRepository dadoSemanalRepository,
                              PontoService pontoService,
                              DiaDaSemanaRepository diaDaSemanaRepository,
                              ItemService itemService) {
        this.dadoSemanalRepository = dadoSemanalRepository;
        this.pontoService = pontoService;
        this.diaDaSemanaRepository = diaDaSemanaRepository;
        this.itemService = itemService;                
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
        return this.dadoSemanalRepository.save(dadoSemanal);
    }

    @Transactional
    public DadoSemanal atualizar(String id, DadoSemanalAtualizarDTO dadoSemanalAtualizarDTO) {
        Optional<DadoSemanal> dadoSemanalOptional = this.dadoSemanalRepository.findById(id);
        if (!dadoSemanalOptional.isPresent()) {
            throw new DadoSemanalBadRequestException("Dado semanal não encontrado.");
        }
        DadoSemanal dadoSemanal = dadoSemanalAtualizarDTO.atualizarDadoSemanal(dadoSemanalOptional.get());
        List<DiaDaSemana> diasDaSemana = this.diaDaSemanaRepository.obterDiasDaSemanaPorIds(dadoSemanalAtualizarDTO.getDiasDaSemanaIds());
        if (diasDaSemana.size() != dadoSemanalAtualizarDTO.getDiasDaSemanaIds().size()) {
            throw new DadoSemanalBadRequestException("Algum(ns) DiaDaSemana não foi encontrado.");
        }
        dadoSemanal.setDiasDaSemana(diasDaSemana);
        return this.dadoSemanalRepository.save(dadoSemanal);
    }

    @Transactional(readOnly = true)
    public Page<Map<String, Object>> obterDadosPorPontoId(Pageable pageable, String pontoId) {
        return this.dadoSemanalRepository.findDadoSemanalsByPontoId(pageable, pontoId);
    }

    @Transactional(readOnly = true)
    public List<DadoSemanalDiaDaSemanaDTO> obterDadosPorPontoIdPorDias(String pontoId) {
        List<DadoSemanalDiaDaSemanaDTO> dadosList =  new ArrayList<>();
        List<DiaDaSemana> diaDaSemanaList = this.diaDaSemanaRepository.findAll();
        diaDaSemanaList.forEach(dia -> {
            DadoSemanalDiaDaSemanaDTO dadoSemanalDiaDaSemanaDTO = new DadoSemanalDiaDaSemanaDTO();
            dadoSemanalDiaDaSemanaDTO.setDiaDaSemana(dia.getNomeDiaSemana().name());
            dadoSemanalDiaDaSemanaDTO.setDadoSemanalList(this.dadoSemanalRepository.findDadoSemanalsByPontoIdAndDiaDaSemanaId(pontoId, dia.getId()));
            dadosList.add(dadoSemanalDiaDaSemanaDTO);
        });
        return dadosList;
    }

    @Transactional(readOnly = true)
    public DadoSemanal obterDadoSemanalPeloId(String id) {
        return this.dadoSemanalRepository.findById(id).orElse(null);
    }

    public void deletarDadoSemanal(String id) {
        this.itemService.deletarItens(id);
        try {
            this.dadoSemanalRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {}     
    }

}
