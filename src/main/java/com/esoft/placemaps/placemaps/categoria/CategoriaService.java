package com.esoft.placemaps.placemaps.categoria;

import java.util.List;
import java.util.Optional;

import com.esoft.placemaps.placemaps.categoria.exception.CategoriaBadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
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
    
}
