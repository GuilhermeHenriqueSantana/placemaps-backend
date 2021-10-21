package com.esoft.placemaps.placemaps.controleponto;

import com.esoft.placemaps.placemaps.avaliacao.AvaliacaoRepository;
import com.esoft.placemaps.placemaps.comentario.ComentarioRepository;
import com.esoft.placemaps.placemaps.controleponto.dto.DashboardDTO;
import com.esoft.placemaps.placemaps.controleponto.exception.ControlePontoBadRequestException;
import com.esoft.placemaps.placemaps.ponto.PontoRepository;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import com.esoft.placemaps.placemaps.usuario.UsuarioEscopo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class ControlePontoService {

    private final ControlePontoRepository controlePontoRepository;
    private final PontoRepository pontoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final ComentarioRepository comentarioRepository;


    @Autowired
    public ControlePontoService(ControlePontoRepository controlePontoRepository,
                                PontoRepository pontoRepository,
                                AvaliacaoRepository avaliacaoRepository,
                                ComentarioRepository comentarioRepository) {
        this.controlePontoRepository = controlePontoRepository;
        this.pontoRepository = pontoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.comentarioRepository = comentarioRepository;
    }

    @Transactional
    public String alterarQuantidadePontosSolicitados(Integer quantidade) {
        Usuario usuario = UsuarioEscopo.usuarioAtual();
        ControlePonto controlePonto = this.controlePontoRepository.findFirstByUsuario(usuario);
        if (controlePonto.getPontosAtivos() != controlePonto.getPontosSolicitados()) {
            throw new ControlePontoBadRequestException("Pedido já em andamento.");
        }
        controlePonto.setPontosSolicitados(quantidade);
        this.controlePontoRepository.save(controlePonto);
        return "OK";
    }

    @Transactional
    public String aceitarNegarSolicitacaoDeAlteracao(String controlePontoId, Boolean aceitar) {
        Optional<ControlePonto> controlePontoOptional = this.controlePontoRepository.findById(controlePontoId);
        if (controlePontoOptional.isPresent()) {
            if (aceitar) {
                controlePontoOptional.get().setPontosAtivos(controlePontoOptional.get().getPontosSolicitados());
            } else {
                controlePontoOptional.get().setPontosSolicitados(controlePontoOptional.get().getPontosAtivos());
            }
            this.controlePontoRepository.save(controlePontoOptional.get());
            return "OK";
        }
        throw new ControlePontoBadRequestException("Controle de ponto não encontrado.");
    }

    @Transactional(readOnly = true)
    public Map<String, Object> obterPeloProprietario() {
        return this.controlePontoRepository.obterPeloProprietario(UsuarioEscopo.usuarioAtual().getId());
    }

    @Transactional(readOnly = true)
    public Page<Map<String, Object>> obterControlesPontoSolicitados(Pageable pageable) {
        return this.controlePontoRepository.obterControlesPontoSolicitados(pageable);
    }

    @Transactional(readOnly = true)
    public DashboardDTO dashboard() {
        DashboardDTO dashboardDTO = new DashboardDTO();
        ControlePonto controlePonto = this.controlePontoRepository.findFirstByUsuarioId(UsuarioEscopo.usuarioAtual().getId());
        dashboardDTO.setQtdePontosPermitidos(controlePonto.getPontosAtivos());
        dashboardDTO.setQtdePontosAtivos(this.pontoRepository.obterQuantidadeDePontosAtivosPeloControle(controlePonto.getId()));
        dashboardDTO.setQtdeAvaliacoes(this.avaliacaoRepository.obterQtdeAvaliacoesPeloControleDePonto(controlePonto.getId()));
        dashboardDTO.setQtdeComentarios(this.comentarioRepository.obterQtdeComentariosPeloControleDePonto(controlePonto.getId()));
        return dashboardDTO;
    }

}
