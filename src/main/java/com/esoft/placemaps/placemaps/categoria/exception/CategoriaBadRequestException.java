package com.esoft.placemaps.placemaps.categoria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CategoriaBadRequestException extends RuntimeException {
    
    public CategoriaBadRequestException(String mensagem) {
        super(mensagem);
    }

}
