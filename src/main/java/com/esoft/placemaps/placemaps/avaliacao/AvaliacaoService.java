package com.esoft.placemaps.placemaps.avaliacao;

import com.esoft.placemaps.placemaps.avaliacao.dto.AvaliacaoFormDTO;
import com.esoft.placemaps.placemaps.avaliacao.dto.RespostaDeAvaliacaoDTO;
import com.esoft.placemaps.placemaps.avaliacao.exception.AvaliacaoBadRequestException;
import com.esoft.placemaps.placemaps.controleponto.ControlePonto;
import com.esoft.placemaps.placemaps.controleponto.ControlePontoRepository;
import com.esoft.placemaps.placemaps.ponto.Ponto;
import com.esoft.placemaps.placemaps.ponto.PontoService;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import com.esoft.placemaps.placemaps.usuario.UsuarioEscopo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final PontoService pontoService;
    private  final ControlePontoRepository controlePontoRepository;

    @Autowired
    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository,
                            PontoService pontoService,
                            ControlePontoRepository controlePontoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.pontoService = pontoService;
        this.controlePontoRepository = controlePontoRepository;
    }

    @Transactional
    public Avaliacao salvar(AvaliacaoFormDTO avaliacaoFormDTO) {
        Avaliacao avaliacao =  avaliacaoFormDTO.gerarAvaliacao();
        avaliacao.validarAvaliacao();
        Ponto ponto = this.pontoService.obterPontoExistente(avaliacaoFormDTO.getPontoId());
        Usuario usuario = UsuarioEscopo.usuarioAtual();
        ControlePonto controlePonto =  this.controlePontoRepository.findFirstByUsuario(usuario);
        if (Objects.nonNull(controlePonto) && controlePonto.getPontos().contains(ponto)) {
            throw new AvaliacaoBadRequestException("Não é possível avaliar o próprio ponto.");
        }
        Optional<Avaliacao> avaliacaoOptional = this.avaliacaoRepository.findFirstByPontoAndUsuario(ponto, usuario);
        if (avaliacaoOptional.isPresent()) {
            avaliacaoOptional.get().setDescricao(avaliacao.getDescricao());
            avaliacaoOptional.get().setNota(avaliacao.getNota());
            return this.avaliacaoRepository.save(avaliacaoOptional.get());
        } else {
            avaliacao.setPonto(ponto);
            avaliacao.setUsuario(usuario);
            return this.avaliacaoRepository.save(avaliacao);
        }
    }

    @Transactional
    public Avaliacao responderAvaliacao(RespostaDeAvaliacaoDTO respostaDeAvaliacaoDTO) {
        Optional<Avaliacao> avaliacaoOptional = this.avaliacaoRepository.findById(respostaDeAvaliacaoDTO.getAvaliacaoId());
        if (avaliacaoOptional.isPresent()) {
            ControlePonto controlePonto = this.controlePontoRepository.findFirstByUsuario(UsuarioEscopo.usuarioAtual());
            if (Objects.nonNull(controlePonto) && controlePonto.getPontos().contains(avaliacaoOptional.get().getPonto())) {
                avaliacaoOptional.get().setResposta(respostaDeAvaliacaoDTO.getResposta());
                return this.avaliacaoRepository.save(avaliacaoOptional.get());
            }
            throw new AvaliacaoBadRequestException("Apenas o proprietário pode responder uma avaliação.");
        }
        throw new AvaliacaoBadRequestException("Avaliação não encontrada.");
    }

    @Transactional(readOnly = true)
    public Page<Map<String, Object>> obterAvaliacoesPeloPonto(Pageable pageable,
                                                              String pontoId) {
        return this.avaliacaoRepository.obterAvaliacoesPeloPonto(pageable, pontoId);
    }

    @Transactional(readOnly = true)
    public Avaliacao obterAvaliacaoPorId(String id) {
        return this.avaliacaoRepository.findById(id).orElse(null);
    }

}
