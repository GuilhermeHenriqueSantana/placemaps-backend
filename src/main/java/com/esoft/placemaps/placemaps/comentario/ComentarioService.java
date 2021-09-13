package com.esoft.placemaps.placemaps.comentario;

import com.esoft.placemaps.placemaps.comentario.dto.ComentarioFormDTO;
import com.esoft.placemaps.placemaps.ponto.PontoService;
import com.esoft.placemaps.placemaps.usuario.UsuarioEscopo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final PontoService pontoService;

    @Autowired
    public ComentarioService(ComentarioRepository comentarioRepository,
                             PontoService pontoService) {
        this.comentarioRepository = comentarioRepository;
        this.pontoService = pontoService;
    }

    @Transactional
    public Comentario salvar(ComentarioFormDTO comentarioFormDTO) {
        Comentario comentario = comentarioFormDTO.gerarComentario();
        comentario.setPonto(this.pontoService.obterPontoExistente(comentarioFormDTO.getPontoId()));
        comentario.setUsuario(UsuarioEscopo.usuarioAtual());
        return this.comentarioRepository.save(comentario);
    }

}
