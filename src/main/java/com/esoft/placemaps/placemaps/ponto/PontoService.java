package com.esoft.placemaps.placemaps.ponto;

import com.esoft.placemaps.placemaps.categoria.CategoriaService;
import com.esoft.placemaps.placemaps.controleponto.ControlePonto;
import com.esoft.placemaps.placemaps.controleponto.ControlePontoRepository;
import com.esoft.placemaps.placemaps.dadosemanal.DadoSemanalRepository;
import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemana;
import com.esoft.placemaps.placemaps.ponto.dto.PontoPageDTO;
import com.esoft.placemaps.placemaps.ponto.exception.PontoBadRequestException;
import com.esoft.placemaps.placemaps.ponto.projection.PontoPageProjection;
import com.esoft.placemaps.placemaps.usuario.UsuarioEscopo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PontoService {

    private final PontoRepository pontoRepository;
    private final ControlePontoRepository controlePontoRepository;
    private final CategoriaService categoriaService;
    private final DadoSemanalRepository dadoSemanalRepository;

    @Autowired
    public PontoService(PontoRepository pontoRepository,
                        ControlePontoRepository controlePontoRepository,
                        CategoriaService categoriaService,
                        DadoSemanalRepository dadoSemanalRepository) {
        this.pontoRepository = pontoRepository;
        this.controlePontoRepository = controlePontoRepository;
        this.categoriaService = categoriaService;
        this.dadoSemanalRepository = dadoSemanalRepository;
    }

    @Transactional
    public String salvar(String categoriaId, Ponto ponto) {
        ControlePonto controlePonto = this.controlePontoRepository.findFirstByUsuario(UsuarioEscopo.usuarioAtual());
        if (Objects.isNull(controlePonto) || (controlePonto.getPontos().size() >= controlePonto.getPontosAtivos())) {
            throw new PontoBadRequestException("Sem permissão para cadastrar ponto.");
        }
        ponto.setCategoria(this.categoriaService.obterCategoriaExistente(categoriaId));
        controlePonto.getPontos().add(this.pontoRepository.save(ponto));
        this.controlePontoRepository.save(controlePonto);
        return "Ok";
    }

    public Ponto obterPontoExistente(String pontoId) {
        Optional<Ponto> pontoOptional = pontoRepository.findById(pontoId);
        if (!pontoOptional.isPresent()) {
            throw new PontoBadRequestException("Ponto não econtrado.");
        }
        return pontoOptional.get();
    }

    @Transactional(readOnly = true)
    public Page<PontoPageDTO> pontoPorNomeECategoria(Pageable pageable, String nome, String categoria) {
        Page<PontoPageDTO> result = new PageImpl<>(new ArrayList<>(), pageable, 0);

        Page<PontoPageProjection> pontoPageProjectionPage = this.pontoRepository.pontoPorNomeECategoria(pageable, nome, categoria);

        if(pontoPageProjectionPage.getTotalElements() > 0) {
            List<PontoPageDTO> collect = pontoPageProjectionPage.getContent()
                    .stream()
                    .map(PontoPageDTO::new)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            for (PontoPageDTO ponto: collect) {
                ponto.setDadoSemanalNomeList(this.dadoSemanalRepository.obterNomesPorPontoId(ponto.getId(),
                        new DiaDaSemana().pegarDiaDaSemana(new Date()).name()));
            }

            result = new PageImpl<>(collect, pageable, pontoPageProjectionPage.getTotalElements());
        }

        return result;
    }
    
}
