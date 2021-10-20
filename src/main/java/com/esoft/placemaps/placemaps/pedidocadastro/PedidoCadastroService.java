package com.esoft.placemaps.placemaps.pedidocadastro;

import com.esoft.placemaps.placemaps.autenticacao.AutenticacaoService;
import com.esoft.placemaps.placemaps.controleponto.ControlePonto;
import com.esoft.placemaps.placemaps.controleponto.ControlePontoRepository;
import com.esoft.placemaps.placemaps.pedidocadastro.dto.AceiteDePedidoDTO;
import com.esoft.placemaps.placemaps.pedidocadastro.exception.PedidoCadastroBadRequestException;
import com.esoft.placemaps.placemaps.plano.PlanoService;
import com.esoft.placemaps.placemaps.usuario.TipoUsuario;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import com.esoft.placemaps.placemaps.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class PedidoCadastroService {

    private final PedidoCadastroRepository pedidoCadastroRepository;
    private final UsuarioRepository usuarioRepository;
    private final AutenticacaoService autenticacaoService;
    private final ControlePontoRepository controlePontoRepository;
    private final PlanoService planoService;

    @Autowired
    public PedidoCadastroService(PedidoCadastroRepository pedidoCadastroRepository,
                                 UsuarioRepository usuarioRepository,
                                 AutenticacaoService autenticacaoService,
                                 ControlePontoRepository controlePontoRepository,
                                 PlanoService planoService) {
        this.pedidoCadastroRepository = pedidoCadastroRepository;
        this.usuarioRepository = usuarioRepository;
        this.autenticacaoService = autenticacaoService;
        this.controlePontoRepository = controlePontoRepository;
        this.planoService = planoService;
    }

    @Transactional
    public PedidoCadastro salvar(PedidoCadastro pedidoCadastro, String planoId) {
        pedidoCadastro.validarPedidoCadastro();
        pedidoCadastro.setPlano(this.planoService.obterPlanoExistente(planoId));
        this.validarEmailExistente(pedidoCadastro);
        this.validarPedidoExistente(pedidoCadastro.getEmail());
        if (Objects.isNull(pedidoCadastro.getUsuario())) {
            pedidoCadastro.setSenha(this.autenticacaoService.criptografarSenha(pedidoCadastro.getSenha()));
        }
        pedidoCadastro.setData(new Date());
        return this.pedidoCadastroRepository.save(pedidoCadastro);
    }

    @Transactional
    public void aceitarPedido(AceiteDePedidoDTO aceiteDePedidoDTO) {
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

            Integer quantidadePontos = Objects.isNull(aceiteDePedidoDTO.getQuantidadeDePontos())
                    ? pedidoCadastroOptional.get().getPlano().getPontos()
                    : aceiteDePedidoDTO.getQuantidadeDePontos();

            this.controlePontoRepository.save(new ControlePonto()
                    .builder()
                    .pontosAtivos(quantidadePontos)
                    .pontosSolicitados(quantidadePontos)
                    .usuario(this.usuarioRepository.save(usuario))
                    .build());

            this.pedidoCadastroRepository.deleteById(aceiteDePedidoDTO.getPedidoId());
        }
        throw new PedidoCadastroBadRequestException("Pedido não encontrado.");
    }

    @Transactional
    public void negarPedido(String id) {
        this.pedidoCadastroRepository.deleteById(id);
    }

    public void validarEmailExistente(PedidoCadastro pedidoCadastro) {
        if (Objects.isNull(pedidoCadastro.getUsuario())) {
            Optional<Usuario> usuarioOptional = this.usuarioRepository.findByEmail(pedidoCadastro.getEmail());
            if (usuarioOptional.isPresent()) {
                throw new PedidoCadastroBadRequestException("Email existente.");
            }
        }
    }

    public void validarPedidoExistente(String email) {
        Optional<PedidoCadastro> pedidoCadastro = this.pedidoCadastroRepository.findFirstByEmail(email);
        if (pedidoCadastro.isPresent()) {
            throw new PedidoCadastroBadRequestException("Email já cadastrado.");
        }
    }

    @Transactional(readOnly = true)
    public Page<PedidoCadastro> obterPedidosCadastrados(Pageable pageable) {
        return this.pedidoCadastroRepository.obterPedidosCadastrados(pageable);
    }

    @Transactional(readOnly = true)
    public PedidoCadastro obterPedidoPeloId(String id) {
        return this.pedidoCadastroRepository.findById(id).orElse(null);
    }

}
