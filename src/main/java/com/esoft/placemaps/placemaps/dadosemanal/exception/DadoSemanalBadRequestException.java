package com.esoft.placemaps.placemaps.dadosemanal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DadoSemanalBadRequestException extends RuntimeException {

    public DadoSemanalBadRequestException(String mensagem) {
        super(mensagem);
    }
}
