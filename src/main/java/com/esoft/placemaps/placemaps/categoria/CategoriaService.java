package com.esoft.placemaps.placemaps.categoria;

import java.util.List;
import java.util.Optional;

import com.esoft.placemaps.placemaps.categoria.exception.CategoriaBadRequestException;

import com.esoft.placemaps.placemaps.ponto.PontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    private final PontoRepository pontoRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository,
                            PontoRepository pontoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.pontoRepository = pontoRepository;
    }

    @Transactional
    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria obterCategoriaExistente(String categoriaId) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(categoriaId);
        if (!categoriaOptional.isPresent()) {
            throw new CategoriaBadRequestException("Categoria não econtrada.");
        }
        return categoriaOptional.get();
    }

    public List<Categoria> obterTodas() {
        return categoriaRepository.findAll();
    } 

    @Transactional
    public void deletarCategoria(String id) {
        if (this.pontoRepository.existePontoComCategoriaId(id)) {
            throw new CategoriaBadRequestException("Categoria já utilizada. Não é possível realizar a exclusão.");
        } else {
            try {
                this.categoriaRepository.deleteById(id);
            } catch (EmptyResultDataAccessException e) {}
        }
    }
}
