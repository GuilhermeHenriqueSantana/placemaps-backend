package com.esoft.placemaps.placemaps.ponto;

import com.esoft.placemaps.placemaps.categoria.Categoria;
import com.esoft.placemaps.placemaps.categoria.CategoriaService;

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
        Categoria categoria = categoriaService.obterCategoriaExistente(categoriaId);
        ponto.setCategoria(categoria);
        return this.pontoRepository.save(ponto);
    }
    
}
