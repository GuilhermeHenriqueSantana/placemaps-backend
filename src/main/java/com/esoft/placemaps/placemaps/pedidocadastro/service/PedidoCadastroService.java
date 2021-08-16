package com.esoft.placemaps.placemaps.pedidocadastro.service;

import com.esoft.placemaps.placemaps.pedidocadastro.PedidoCadastro;
import com.esoft.placemaps.placemaps.pedidocadastro.repository.PedidoCadastroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoCadastroService {
    private final PedidoCadastroRepository pedidoCadastroRepository;

    @Autowired
    public PedidoCadastroService(PedidoCadastroRepository pedidoCadastroRepository) {
        this.pedidoCadastroRepository = pedidoCadastroRepository;
    }

    @Transactional
    public PedidoCadastro save(PedidoCadastro pedidoCadastro) {
        return this.pedidoCadastroRepository.save(pedidoCadastro);
    }
}
