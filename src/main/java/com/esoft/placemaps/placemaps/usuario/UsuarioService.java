package com.esoft.placemaps.placemaps.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.esoft.placemaps.placemaps.evento.Evento;
import com.esoft.placemaps.placemaps.evento.EventoRepository;
import com.esoft.placemaps.placemaps.usuario.exception.UsuarioBadRequestException;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final EventoRepository eventoRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, EventoRepository eventoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    public Optional<Usuario> usuarioAtual() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioRepository.findByEmail(((User) principal).getUsername());
    }

    public Usuario cadastrarNoEvento(String eventoId) {
        Optional<Usuario> usuarioOptional = usuarioAtual();
        if (!usuarioOptional.isPresent()) {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }
        Optional<Evento> eventoOptional = eventoRepository.findById(eventoId);
        if (!eventoOptional.isPresent()) {
            throw new UsuarioBadRequestException("Evento não econtrado.");
        }
        Usuario usuario = usuarioOptional.get();
        if (usuario.getEventos().contains(eventoOptional.get())) {
            throw new UsuarioBadRequestException("Usuário já está cadastrado nesse evento");
        };
        usuario.getEventos().add(eventoOptional.get());
        return usuarioRepository.save(usuario);
    }
}
