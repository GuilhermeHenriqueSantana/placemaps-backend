package com.esoft.placemaps.placemaps.comentario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ComentarioBadRequestException extends RuntimeException {

    public ComentarioBadRequestException(String mensagem) {
        super(mensagem);
    }

}
