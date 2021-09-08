package com.esoft.placemaps.placemaps.ponto;

import com.esoft.placemaps.placemaps.categoria.CategoriaService;
import com.esoft.placemaps.placemaps.controleponto.ControlePonto;
import com.esoft.placemaps.placemaps.controleponto.ControlePontoRepository;
import com.esoft.placemaps.placemaps.ponto.exception.PontoBadRequestException;
import com.esoft.placemaps.placemaps.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class PontoService {

    private final PontoRepository pontoRepository;
    private final UsuarioService usuarioService;
    private final ControlePontoRepository controlePontoRepository;
    private final CategoriaService categoriaService;

    @Autowired
    public PontoService(PontoRepository pontoRepository,
                        UsuarioService usuarioService,
                        ControlePontoRepository controlePontoRepository,
                        CategoriaService categoriaService) {
        this.pontoRepository = pontoRepository;
        this.usuarioService = usuarioService;
        this.controlePontoRepository = controlePontoRepository;
        this.categoriaService = categoriaService;
    }

    @Transactional
    public String salvar(String categoriaId, Ponto ponto) {
        ControlePonto controlePonto = this.controlePontoRepository.findFirstByUsuario(this.usuarioService.usuarioAtual().get());
        if (Objects.isNull(controlePonto) || (controlePonto.getPontos().size() >= controlePonto.getPontosAtivos())) {
            throw new PontoBadRequestException("Sem permissão para cadastrar ponto.");
        }
        ponto.setCategoria(this.categoriaService.obterCategoriaExistente(categoriaId));
        controlePonto.getPontos().add(this.pontoRepository.save(ponto));
        controlePontoRepository.save(controlePonto);
        return "Ok";
    }

    public Ponto obterPontoExistente(String pontoId) {
        Optional<Ponto> pontoOptional = pontoRepository.findById(pontoId);
        if (!pontoOptional.isPresent()) {
            throw new PontoBadRequestException("Ponto não econtrado.");
        }
        return pontoOptional.get();
    }
    
}
