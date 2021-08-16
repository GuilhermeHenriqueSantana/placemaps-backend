package com.esoft.placemaps.placemaps.plano.datasource.implementation;

import com.esoft.placemaps.placemaps.plano.Plano;
import com.esoft.placemaps.placemaps.plano.repository.PlanoRepository;
import com.esoft.placemaps.placemaps.plano.datasource.PlanoDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlanoDataSourceImpl implements PlanoDataSource {
    private PlanoRepository planoRepository;

    @Autowired
    public PlanoDataSourceImpl(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
    }

    @Override
    public Plano save(Plano plano) {
        return this.planoRepository.save(plano);
    }
}
