package com.esoft.placemaps.placemaps.mensagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;

    @Autowired
    public MensagemService(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    @Transactional
    public Mensagem salvar(Mensagem mensagem) {
        return this.mensagemRepository.save(mensagem);
    }

}
