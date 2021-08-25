package com.esoft.placemaps.placemaps.pedidocadastro;

import com.esoft.placemaps.placemaps.autenticacao.AutenticacaoService;
import com.esoft.placemaps.placemaps.controleponto.ControlePonto;
import com.esoft.placemaps.placemaps.controleponto.ControlePontoRepository;
import com.esoft.placemaps.placemaps.pedidocadastro.dto.AceiteDePedidoDTO;
import com.esoft.placemaps.placemaps.pedidocadastro.exception.PedidoCadastroBadRequestException;
import com.esoft.placemaps.placemaps.plano.Plano;
import com.esoft.placemaps.placemaps.plano.PlanoRepository;
import com.esoft.placemaps.placemaps.usuario.TipoUsuario;
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
    private final AutenticacaoService autenticacaoService;
    private final ControlePontoRepository controlePontoRepository;

    @Autowired
    public PedidoCadastroService(PedidoCadastroRepository pedidoCadastroRepository,
                                 PlanoRepository planoRepository,
                                 UsuarioRepository usuarioRepository,
                                 AutenticacaoService autenticacaoService,
                                 ControlePontoRepository controlePontoRepository) {
        this.pedidoCadastroRepository = pedidoCadastroRepository;
        this.planoRepository = planoRepository;
        this.usuarioRepository = usuarioRepository;
        this.autenticacaoService = autenticacaoService;
        this.controlePontoRepository = controlePontoRepository;
    }

    @Transactional
    public PedidoCadastro salvar(PedidoCadastro pedidoCadastro) {
        pedidoCadastro.validarPedidoCadastro();
        this.validarPlanoVinculado(pedidoCadastro.getPlano());
        this.validarEmailExistente(pedidoCadastro);
        pedidoCadastro.setSenha(this.autenticacaoService.criptografarSenha(pedidoCadastro.getSenha()));
        return this.pedidoCadastroRepository.save(pedidoCadastro);
    }

    @Transactional
    public String aceitarPedido(AceiteDePedidoDTO aceiteDePedidoDTO) {
        Optional<PedidoCadastro> pedidoCadastroOptional = this.pedidoCadastroRepository.findById(aceiteDePedidoDTO.getPedidoId());
        if (pedidoCadastroOptional.isPresent()) {
            Usuario usuario = new Usuario();
            if (Objects.nonNull(pedidoCadastroOptional.get().getUsuario())) {
                usuario = pedidoCadastroOptional.get().getUsuario();
            } else {
                usuario.setEmail(pedidoCadastroOptional.get().getEmail());
                usuario.setNome(pedidoCadastroOptional.get().getNome());
                usuario.setSenha(pedidoCadastroOptional.get().getSenha());
            }
            usuario.setTipoUsuario(TipoUsuario.PROPRIETARIO);
            usuario.setNumeracaoDocumento(pedidoCadastroOptional.get().getNumeracaoDocumento());

            this.controlePontoRepository.save(new ControlePonto()
                    .builder()
                    .pontosAtivos(aceiteDePedidoDTO.getQuantidadeDePontos())
                    .pontosSolicitados(aceiteDePedidoDTO.getQuantidadeDePontos())
                    .usuario(this.usuarioRepository.save(usuario))
                    .build());

            this.pedidoCadastroRepository.deleteById(aceiteDePedidoDTO.getPedidoId());
            return "OK";
        }
        throw new PedidoCadastroBadRequestException("Pedido não encontrado.");
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
