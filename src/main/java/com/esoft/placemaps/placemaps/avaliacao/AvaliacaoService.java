package com.esoft.placemaps.placemaps.avaliacao;

import com.esoft.placemaps.placemaps.avaliacao.dto.AvaliacaoFormDTO;
import com.esoft.placemaps.placemaps.avaliacao.dto.RespostaDeAvaliacaoDTO;
import com.esoft.placemaps.placemaps.avaliacao.exception.AvaliacaoBadRequestException;
import com.esoft.placemaps.placemaps.ponto.Ponto;
import com.esoft.placemaps.placemaps.ponto.PontoRepository;
import com.esoft.placemaps.placemaps.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final PontoRepository pontoRepository;
    private final UsuarioService usuarioService;

    @Autowired
    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository,
                            PontoRepository pontoRepository,
                            UsuarioService usuarioService) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.pontoRepository = pontoRepository;
        this.usuarioService = usuarioService;
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
            avaliacaoOptional.get().setResposta(respostaDeAvaliacaoDTO.getResposta());
            return this.avaliacaoRepository.save(avaliacaoOptional.get());
        }
        throw new AvaliacaoBadRequestException("Avaliação não encontrada.");
    }

}
