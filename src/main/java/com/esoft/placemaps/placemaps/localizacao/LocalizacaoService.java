package com.esoft.placemaps.placemaps.localizacao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalizacaoService {
    
    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Transactional
    public Localizacao save(Localizacao localizacao) {
        return this.localizacaoRepository.save(localizacao);
    }

    public List<Localizacao> pegarLocalizacoesPeloId(List<String> ids) {
        List<Localizacao> localizacoes = new ArrayList<>();

        for (String id : ids) {
            localizacoes.add(localizacaoRepository.getById(id));
        }

        return localizacoes;
    }

}
