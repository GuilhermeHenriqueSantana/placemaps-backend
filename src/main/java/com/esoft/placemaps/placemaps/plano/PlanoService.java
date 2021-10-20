package com.esoft.placemaps.placemaps.plano;

import com.esoft.placemaps.placemaps.plano.exception.PlanoBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PlanoService {

    private final PlanoRepository planoRepository;

    @Autowired
    public PlanoService(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
    }

    @Transactional
    public Plano salvar(Plano plano) {
        return this.planoRepository.save(plano);
    }

    public Plano obterPlanoExistente(String planoId) {
        Optional<Plano> planoOptional = this.planoRepository.findById(planoId);
        if (!planoOptional.isPresent()) {
            throw new PlanoBadRequestException("Plano não encontrado.");
        }
        return planoOptional.get();
    }

    @Transactional(readOnly = true)
    public Page<Plano> pegarPlanos(Pageable pageable) {
        return this.planoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Plano pegarPlanoPeloId(String id) {
        return this.planoRepository.findById(id).orElse(null);
    }
}

