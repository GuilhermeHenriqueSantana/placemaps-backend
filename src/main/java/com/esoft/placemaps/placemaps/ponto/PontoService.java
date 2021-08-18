package com.esoft.placemaps.placemaps.ponto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PontoService {

    private final PontoRepository pontoRepository;

    @Autowired
    public PontoService(PontoRepository pontoRepository) {
        this.pontoRepository = pontoRepository;
    }

    @Transactional
    public Ponto salvar(Ponto ponto) {
        return this.pontoRepository.save(ponto);
    }
    
}
