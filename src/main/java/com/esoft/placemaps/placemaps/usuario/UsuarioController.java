package com.esoft.placemaps.placemaps.usuario;

import com.esoft.placemaps.placemaps.usuario.dto.AtualizarUsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PreAuthorize("hasRole('PROPRIETARIO') or hasRole('USUARIO')")
    @PutMapping("/lembrar-evento/{eventoId}")
    public ResponseEntity<String> lembrarEvento(@PathVariable String eventoId) {
        usuarioService.manterLembrete(eventoId, true);
        return ResponseEntity.ok("Ok");
    }

    @PreAuthorize("hasRole('PROPRIETARIO') or hasRole('USUARIO')")
    @PutMapping("/esquecer-evento/{eventoId}")
    public ResponseEntity<String> esquecerEvento(@PathVariable String eventoId) {
        usuarioService.manterLembrete(eventoId, false);
        return ResponseEntity.ok("Ok");
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @PutMapping("/atualizar-documento")
    public ResponseEntity atualizarDocumento(@RequestParam(value = "documento") String documento) {
        this.usuarioService.atualizarDocumento(documento);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PutMapping
    public ResponseEntity atualizarUsuario(@RequestBody AtualizarUsuarioDTO atualizarUsuarioDTO) {
        this.usuarioService.atualizar(atualizarUsuarioDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
