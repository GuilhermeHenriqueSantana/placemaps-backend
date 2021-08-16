package com.esoft.placemaps.placemaps.plano.service;

import com.esoft.placemaps.placemaps.plano.Plano;
import com.esoft.placemaps.placemaps.plano.datasource.PlanoDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanoService {
    private final PlanoDataSource planoDataSource;

    @Autowired
    public PlanoService(PlanoDataSource planoDataSource) {
        this.planoDataSource = planoDataSource;
    }

    @Transactional
    public Plano save(Plano plano) {
        return this.planoDataSource.save(plano);
    }
}

