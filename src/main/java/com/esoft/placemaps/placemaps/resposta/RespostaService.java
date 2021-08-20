package com.esoft.placemaps.placemaps.resposta;

import com.esoft.placemaps.placemaps.comentario.Comentario;
import com.esoft.placemaps.placemaps.comentario.ComentarioRepository;
import com.esoft.placemaps.placemaps.resposta.dto.RespostaFormDTO;
import com.esoft.placemaps.placemaps.resposta.exception.RespostaBadRequestException;
import com.esoft.placemaps.placemaps.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RespostaService {

    private final RespostaRepository respostaRepository;
    private final ComentarioRepository comentarioRepository;
    private final UsuarioService usuarioService;

    @Autowired
    public RespostaService(RespostaRepository respostaRepository,
                           ComentarioRepository comentarioRepository,
                           UsuarioService usuarioService) {
        this.respostaRepository = respostaRepository;
        this.comentarioRepository = comentarioRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public Resposta salvar(RespostaFormDTO respostaFormDTO) {
        Resposta resposta = respostaFormDTO.gerarResposta();
        Optional<Comentario> comentarioOptional = this.comentarioRepository.findById(respostaFormDTO.getComentarioId());
        if (comentarioOptional.isPresent()) {
            resposta.setComentario(comentarioOptional.get());
            resposta.setUsuario(this.usuarioService.usuarioAtual().get());
            return this.respostaRepository.save(resposta);
        }
        throw new RespostaBadRequestException("Comentário não encontrado.");
    }

}
