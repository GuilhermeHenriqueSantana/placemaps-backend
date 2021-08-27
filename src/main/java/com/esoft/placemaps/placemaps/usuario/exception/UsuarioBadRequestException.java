package com.esoft.placemaps.placemaps.usuario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UsuarioBadRequestException extends RuntimeException {
    
    public UsuarioBadRequestException(String mensagem) {
        super(mensagem);
    }

}
