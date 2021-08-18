package com.esoft.placemaps.placemaps.ponto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PontoService {

    @Autowired
    private PontoRepository pontoRepository;

    @Transactional
    public Ponto salvar(Ponto ponto) {
        return this.pontoRepository.save(ponto);
    }
    
}
