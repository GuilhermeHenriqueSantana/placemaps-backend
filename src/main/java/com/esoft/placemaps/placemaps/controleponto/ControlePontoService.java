package com.esoft.placemaps.placemaps.controleponto;

import com.esoft.placemaps.placemaps.controleponto.exception.ControlePontoBadRequestException;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import com.esoft.placemaps.placemaps.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ControlePontoService {

    private final ControlePontoRepository controlePontoRepository;

    private final UsuarioService usuarioService;

    @Autowired
    public ControlePontoService(ControlePontoRepository controlePontoRepository,
                                UsuarioService usuarioService) {
        this.controlePontoRepository = controlePontoRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public String alterarQuantidadePontosSolicitados(Integer quantidade) {
        Usuario usuario = this.usuarioService.usuarioAtual().get();
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

}
