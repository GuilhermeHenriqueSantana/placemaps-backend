package com.esoft.placemaps.placemaps.ponto;

import java.util.Optional;

import com.esoft.placemaps.placemaps.categoria.Categoria;
import com.esoft.placemaps.placemaps.categoria.CategoriaService;
import com.esoft.placemaps.placemaps.ponto.exception.PontoBadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PontoService {

    private final PontoRepository pontoRepository;

    private final CategoriaService categoriaService;

    @Autowired
    public PontoService(PontoRepository pontoRepository, CategoriaService categoriaService) {
        this.pontoRepository = pontoRepository;
        this.categoriaService = categoriaService;
    }

    @Transactional
    public Ponto salvar(String categoriaId, Ponto ponto) {
        ponto.setCategoria(categoriaService.obterCategoriaExistente(categoriaId));
        return this.pontoRepository.save(ponto);
    }

    public Ponto obterPontoExistente(String pontoId) {
        Optional<Ponto> pontoOptional = pontoRepository.findById(pontoId);
        if (!pontoOptional.isPresent()) {
            throw new PontoBadRequestException("Ponto não econtrado.");
        }
        return pontoOptional.get();
    }
    
}
