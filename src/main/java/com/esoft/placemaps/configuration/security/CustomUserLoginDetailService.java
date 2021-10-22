package com.esoft.placemaps.configuration.security;

import java.util.List;
import java.util.Optional;

import com.esoft.placemaps.placemaps.usuario.Usuario;
import com.esoft.placemaps.placemaps.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserLoginDetailService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public CustomUserLoginDetailService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional =  usuarioRepository.findByEmail(email);
        if (!usuarioOptional.isPresent()) {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }
        Usuario usuario = usuarioOptional.get();
        List<GrantedAuthority> authoritiesAdmin = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        List<GrantedAuthority> authoritiesUsuario = AuthorityUtils.createAuthorityList("ROLE_USUARIO");
        List<GrantedAuthority> authoritiesProprietario = AuthorityUtils.createAuthorityList("ROLE_PROPRIETARIO");

        List<GrantedAuthority> authoritiesCurrent;

        switch (usuario.getTipoUsuario().toString()) {
            case "ADMIN":
                authoritiesCurrent = authoritiesAdmin;
                break;
            case "USUARIO":
                authoritiesCurrent = authoritiesUsuario;
                break;
            case "PROPRIETARIO":
                authoritiesCurrent = authoritiesProprietario;
                break;
            default:
                authoritiesCurrent = null;
                break;
        }
        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getSenha(), authoritiesCurrent);
    }
}
