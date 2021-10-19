package com.esoft.placemaps.placemaps.opniao;

import com.esoft.placemaps.placemaps.usuario.UsuarioEscopo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OpiniaoService {

    private final OpiniaoRepository opiniaoRepository;

    @Autowired
    public OpiniaoService(OpiniaoRepository opiniaoRepository) {
        this.opiniaoRepository = opiniaoRepository;
    }

    @Transactional
    public Opiniao salvar(String descricao) {
        return this.opiniaoRepository.save(new Opiniao(descricao, new Date(), UsuarioEscopo.usuarioAtual()));
    }

    @Transactional(readOnly = true)
    public Page<Opiniao> getPageOrderByDateDesc(Pageable pageable) {
        return this.opiniaoRepository.getPageOrderByDateDesc(pageable);
    }

}
