package com.esoft.placemaps.placemaps.avaliacao;

import com.esoft.placemaps.placemaps.avaliacao.dto.AvaliacaoFormDTO;
import com.esoft.placemaps.placemaps.avaliacao.dto.RespostaDeAvaliacaoDTO;
import com.esoft.placemaps.placemaps.avaliacao.exception.AvaliacaoBadRequestException;
import com.esoft.placemaps.placemaps.controleponto.ControlePonto;
import com.esoft.placemaps.placemaps.controleponto.ControlePontoRepository;
import com.esoft.placemaps.placemaps.ponto.Ponto;
import com.esoft.placemaps.placemaps.ponto.PontoRepository;
import com.esoft.placemaps.placemaps.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final PontoRepository pontoRepository;
    private final UsuarioService usuarioService;
    private  final ControlePontoRepository controlePontoRepository;

    @Autowired
    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository,
                            PontoRepository pontoRepository,
                            UsuarioService usuarioService,
                            ControlePontoRepository controlePontoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.pontoRepository = pontoRepository;
        this.usuarioService = usuarioService;
        this.controlePontoRepository = controlePontoRepository;
    }

    @Transactional
    public Avaliacao salvar(AvaliacaoFormDTO avaliacaoFormDTO) {
        Avaliacao avaliacao =  avaliacaoFormDTO.gerarAvaliacao();
        avaliacao.validarAvaliacao();
        Optional<Ponto> pontoOptional = this.pontoRepository.findById(avaliacaoFormDTO.getPontoId());
        if (pontoOptional.isPresent()) {
            Optional<Avaliacao> avaliacaoOptional = this.avaliacaoRepository.findFirstByPontoAndUsuario(pontoOptional.get(), this.usuarioService.usuarioAtual().get());
            if (avaliacaoOptional.isPresent()) {
                avaliacaoOptional.get().setDescricao(avaliacao.getDescricao());
                avaliacaoOptional.get().setNota(avaliacao.getNota());
                return this.avaliacaoRepository.save(avaliacaoOptional.get());
            } else {
                avaliacao.setPonto(pontoOptional.get());
                avaliacao.setUsuario(this.usuarioService.usuarioAtual().get());
                return this.avaliacaoRepository.save(avaliacao);
            }
        }
        throw new AvaliacaoBadRequestException("Ponto não encontrado.");
    }

    @Transactional
    public Avaliacao responderAvaliacao(RespostaDeAvaliacaoDTO respostaDeAvaliacaoDTO) {
        Optional<Avaliacao> avaliacaoOptional = this.avaliacaoRepository.findById(respostaDeAvaliacaoDTO.getAvaliacaoId());
        if (avaliacaoOptional.isPresent()) {
            ControlePonto controlePonto = this.controlePontoRepository.findFirstByUsuario(this.usuarioService.usuarioAtual().get());
            if (Objects.nonNull(controlePonto) && controlePonto.getPontos().contains(avaliacaoOptional.get().getPonto())) {
                avaliacaoOptional.get().setResposta(respostaDeAvaliacaoDTO.getResposta());
                return this.avaliacaoRepository.save(avaliacaoOptional.get());
            }
            throw new AvaliacaoBadRequestException("Apenas o proprietário pode responder uma avaliação.");
        }
        throw new AvaliacaoBadRequestException("Avaliação não encontrada.");
    }

}
