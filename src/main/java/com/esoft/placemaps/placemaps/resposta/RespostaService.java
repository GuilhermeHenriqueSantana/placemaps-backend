package com.esoft.placemaps.placemaps.resposta;

import com.esoft.placemaps.placemaps.comentario.Comentario;
import com.esoft.placemaps.placemaps.comentario.ComentarioRepository;
import com.esoft.placemaps.placemaps.resposta.dto.RespostaFormDTO;
import com.esoft.placemaps.placemaps.resposta.exception.RespostaBadRequestException;
import com.esoft.placemaps.placemaps.usuario.UsuarioEscopo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class RespostaService {

    private final RespostaRepository respostaRepository;
    private final ComentarioRepository comentarioRepository;

    @Autowired
    public RespostaService(RespostaRepository respostaRepository,
                           ComentarioRepository comentarioRepository) {
        this.respostaRepository = respostaRepository;
        this.comentarioRepository = comentarioRepository;
    }

    @Transactional
    public Resposta salvar(RespostaFormDTO respostaFormDTO) {
        Resposta resposta = respostaFormDTO.gerarResposta();
        Optional<Comentario> comentarioOptional = this.comentarioRepository.findById(respostaFormDTO.getComentarioId());
        if (comentarioOptional.isPresent()) {
            resposta.setComentario(comentarioOptional.get());
            resposta.setUsuario(UsuarioEscopo.usuarioAtual());
            return this.respostaRepository.save(resposta);
        }
        throw new RespostaBadRequestException("Comentário não encontrado.");
    }

    @Transactional(readOnly = true)
    public Page<Map<String, Object>> obterRespostasPeloComentarioId(Pageable pageable,
                                                                    String comentarioId) {
        return this.respostaRepository.obterRespostasPeloPonto(pageable, comentarioId);
    }

    @Transactional(readOnly = true)
    public Resposta obterRespostaPeloId(String id) {
        return this.respostaRepository.findById(id).orElse(null);
    }

}
