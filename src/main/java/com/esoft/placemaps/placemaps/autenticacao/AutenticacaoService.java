package com.esoft.placemaps.placemaps.autenticacao;

import com.esoft.placemaps.helpers.SenhaHelper;
import com.esoft.placemaps.placemaps.autenticacao.dto.RespostaLoginDTO;
import com.esoft.placemaps.placemaps.autenticacao.dto.TrocaDeSenhaDTO;
import com.esoft.placemaps.placemaps.usuario.TipoUsuario;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import com.esoft.placemaps.placemaps.usuario.UsuarioRepository;
import com.esoft.placemaps.placemaps.usuario.exception.UsuarioBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Objects;

@Service
public class AutenticacaoService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository =  usuarioRepository;
    }

    @Transactional
    public RespostaLoginDTO cadastrarUsuario(Usuario usuario) {
        usuario.validarUsuario();
        if (this.usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new UsuarioBadRequestException("Email já cadastrado no sistema.");
        }
        usuario.setTipoUsuario(TipoUsuario.USUARIO);
        String senha = usuario.getSenha();
        usuario.setSenha(this.criptografarSenha(senha));
        usuarioRepository.save(usuario);
        return realizarLogin(usuario.getEmail(), senha);
    }

    public String gerarToken(String email, String senha) {
        return "Basic " + new String(Base64.getEncoder().encode((email + ":" + senha).getBytes()));
    }

    public String criptografarSenha(String senha) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(senha);
    }

    public RespostaLoginDTO realizarLogin(String email, String senha) {
        Usuario usuario = this.usuarioRepository.findByEmail(email).get();
        RespostaLoginDTO respostaLoginDTO = new RespostaLoginDTO();
        respostaLoginDTO.setToken(this.gerarToken(email, senha));
        respostaLoginDTO.setNome(usuario.getNome());
        respostaLoginDTO.setEmail(usuario.getEmail());
        respostaLoginDTO.setTipoUsuario(usuario.getTipoUsuario());
        respostaLoginDTO.setFoto(Objects.isNull(usuario.getFoto()) ? null : usuario.getFoto().getUrl());
        return respostaLoginDTO;
    }

    public RespostaLoginDTO trocarSenha(TrocaDeSenhaDTO trocaDeSenhaDTO) {
        if (!SenhaHelper.senhaSegura(trocaDeSenhaDTO.getNovaSenha())) {
            throw new UsuarioBadRequestException("Senha insegura.");
        }
        Usuario usuario = this.usuarioRepository.findByEmail(trocaDeSenhaDTO.getEmail()).get();
        usuario.setSenha(this.criptografarSenha(trocaDeSenhaDTO.getNovaSenha()));
        this.usuarioRepository.save(usuario);
        return this.realizarLogin(trocaDeSenhaDTO.getEmail(), trocaDeSenhaDTO.getNovaSenha());
    }

}



