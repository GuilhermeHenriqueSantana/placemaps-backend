package com.esoft.placemaps.placemaps.usuario;

import com.esoft.placemaps.helpers.DocumentoHelper;
import com.esoft.placemaps.placemaps.usuario.exception.UsuarioBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import com.esoft.placemaps.placemaps.evento.Evento;
import com.esoft.placemaps.placemaps.evento.EventoService;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final EventoService eventoService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, EventoService eventoService) {
        this.usuarioRepository = usuarioRepository;
        this.eventoService = eventoService;
    }

    @Transactional
    public Usuario manterLembrete(String eventoId, Boolean lembrar) {
        Usuario usuario = UsuarioEscopo.usuarioAtual();
        Evento evento = eventoService.obterEventoExistente(eventoId);
        if (usuario.getEventos().stream().filter(e -> e.getId().equals(evento.getId())).findFirst().isPresent()) {
            if (lembrar) {
                return usuario;
            }
            usuario.setEventos(usuario.getEventos().stream().filter(e -> !e.getId().equals(evento.getId())).collect(Collectors.toList()));
            return usuarioRepository.save(usuario);
        }
        if (lembrar) {
            usuario.getEventos().add(evento);
            return usuarioRepository.save(usuario);
        }
        return usuario;
    }

    @Transactional
    public void atualizarDocumento(String documento) {
        if (!DocumentoHelper.documentoValido(documento)) {
         throw new UsuarioBadRequestException("Documento inválido.");
        }
        Usuario usuario = UsuarioEscopo.usuarioAtual();
        usuario.setNumeracaoDocumento(documento);
        this.usuarioRepository.save(usuario);
    }

}
