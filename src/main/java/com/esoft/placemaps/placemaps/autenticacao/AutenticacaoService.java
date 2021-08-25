package com.esoft.placemaps.placemaps.autenticacao;

import com.esoft.placemaps.placemaps.usuario.TipoUsuario;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import com.esoft.placemaps.placemaps.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service
public class AutenticacaoService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository =  usuarioRepository;
    }

    @Transactional
    public String cadastrarUsuario(Usuario usuario) {
        usuario.setTipoUsuario(TipoUsuario.USUARIO);
        String senha = usuario.getSenha();
        usuario.setSenha(this.criptografarSenha(senha));
        usuarioRepository.save(usuario);
        return gerarToken(usuario.getEmail(), senha);
    }

    public String gerarToken(String email, String senha) {
        return "Basic " + new String(Base64.getEncoder().encode((email + ":" + senha).getBytes()));
    }

    public String criptografarSenha(String senha) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(senha);
    }

}



