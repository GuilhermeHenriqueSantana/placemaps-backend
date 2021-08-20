package com.esoft.placemaps.placemaps.pedidocadastro;

import com.esoft.placemaps.placemaps.pedidocadastro.exception.PedidoCadastroBadRequestException;
import com.esoft.placemaps.placemaps.plano.Plano;
import com.esoft.placemaps.placemaps.plano.PlanoRepository;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import com.esoft.placemaps.placemaps.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class PedidoCadastroService {

    private final PedidoCadastroRepository pedidoCadastroRepository;
    private final PlanoRepository planoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public PedidoCadastroService(PedidoCadastroRepository pedidoCadastroRepository,
                                 PlanoRepository planoRepository,
                                 UsuarioRepository usuarioRepository) {
        this.pedidoCadastroRepository = pedidoCadastroRepository;
        this.planoRepository = planoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public PedidoCadastro salvar(PedidoCadastro pedidoCadastro) {
        pedidoCadastro.validarPedidoCadastro();
        this.validarPlanoVinculado(pedidoCadastro.getPlano());
        this.validarEmailExistente(pedidoCadastro);
        return this.pedidoCadastroRepository.save(pedidoCadastro);
    }

    public void validarPlanoVinculado(Plano plano) {
        if (Objects.isNull(plano) || Objects.isNull(plano.getId())) {
            throw new PedidoCadastroBadRequestException("Plano inválido.");
        }
        Optional<Plano> planoOptional = this.planoRepository.findById(plano.getId());
        if (!planoOptional.isPresent()) {
            throw new PedidoCadastroBadRequestException("Plano não encontrado.");
        }
    }

    public void validarEmailExistente(PedidoCadastro pedidoCadastro) {
        if (Objects.isNull(pedidoCadastro.getUsuario())) {
            Optional<Usuario> usuarioOptional = this.usuarioRepository.findByEmail(pedidoCadastro.getEmail());
            if (usuarioOptional.isPresent()) {
                throw new PedidoCadastroBadRequestException("Email existente.");
            }
        }
    }

}
