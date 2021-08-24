package com.esoft.placemaps.placemaps.resposta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RespostaBadRequestException extends RuntimeException {
    public RespostaBadRequestException(String mensagem) {
        super(mensagem);
    }
}