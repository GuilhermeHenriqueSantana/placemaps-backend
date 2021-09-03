package com.esoft.placemaps.placemaps.ponto;

import com.esoft.placemaps.placemaps.controleponto.ControlePonto;
import com.esoft.placemaps.placemaps.controleponto.ControlePontoRepository;
import com.esoft.placemaps.placemaps.ponto.exception.PontoBadRequestException;
import com.esoft.placemaps.placemaps.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class PontoService {

    private final PontoRepository pontoRepository;
    private final UsuarioService usuarioService;
    private final ControlePontoRepository controlePontoRepository;

    @Autowired
    public PontoService(PontoRepository pontoRepository,
                        UsuarioService usuarioService,
                        ControlePontoRepository controlePontoRepository) {
        this.pontoRepository = pontoRepository;
        this.usuarioService = usuarioService;
        this.controlePontoRepository = controlePontoRepository;
    }

    @Transactional
    public String salvar(Ponto ponto) {
        ControlePonto controlePonto = this.controlePontoRepository.findFirstByUsuario(this.usuarioService.usuarioAtual().get());
        if (Objects.isNull(controlePonto) || (controlePonto.getPontos().size() >= controlePonto.getPontosAtivos())) {
            throw new PontoBadRequestException("Sem permissão para cadastrar ponto.");
        }
        controlePonto.getPontos().add(this.pontoRepository.save(ponto));
        controlePontoRepository.save(controlePonto);
        return "Ok";
    }
    
}
