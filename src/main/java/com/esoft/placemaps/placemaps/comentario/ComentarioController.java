package com.esoft.placemaps.placemaps.comentario;

import com.esoft.placemaps.placemaps.comentario.dto.ComentarioFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comentario")
public class ComentarioController {

    private final ComentarioService comentarioService;

    @Autowired
    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping
    public ResponseEntity<Comentario> salvar(@RequestBody ComentarioFormDTO comentarioFormDTO) {
        return ResponseEntity.ok(this.comentarioService.salvar(comentarioFormDTO));
    }

}
