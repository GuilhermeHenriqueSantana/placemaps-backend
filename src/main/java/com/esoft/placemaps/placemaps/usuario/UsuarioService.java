package com.esoft.placemaps.placemaps.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.esoft.placemaps.placemaps.evento.Evento;
import com.esoft.placemaps.placemaps.evento.EventoRepository;
import com.esoft.placemaps.placemaps.evento.EventoService;
import com.esoft.placemaps.placemaps.usuario.exception.UsuarioBadRequestException;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final EventoService eventoService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, EventoService eventoService) {
        this.usuarioRepository = usuarioRepository;
        this.eventoService = eventoService;
    }

    public Usuario manterLembrete(String eventoId, Boolean lembrar) {
        Usuario usuario = UsuarioEscopo.usuarioAtual();
        Evento evento = eventoService.obterEventoExistente(eventoId);
        if (usuario.getEventos().contains(evento)) {
            if (lembrar) {
                return usuario;
            }
            usuario.getEventos().remove(evento);
            return usuarioRepository.save(usuario);
        }
        if (lembrar) {
            usuario.getEventos().add(evento);
            return usuarioRepository.save(usuario);
        }
        return usuario;
    }

}
