package com.esoft.placemaps.placemaps.plano;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanoService {
    private final PlanoRepository planoRepository;

    @Autowired
    public PlanoService(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
    }

    @Transactional
    public Plano save(Plano plano) {
        return this.planoRepository.save(plano);
    }
}

