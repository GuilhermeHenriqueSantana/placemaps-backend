package com.esoft.placemaps.placemaps.controleponto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ControlePontoBadRequestException extends RuntimeException{

    public ControlePontoBadRequestException(String mensagem) {
        super(mensagem);
    }

}
