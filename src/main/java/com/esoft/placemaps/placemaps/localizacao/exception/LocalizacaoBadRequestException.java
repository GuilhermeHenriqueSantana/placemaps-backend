package com.esoft.placemaps.placemaps.localizacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class LocalizacaoBadRequestException extends RuntimeException{

    public LocalizacaoBadRequestException(String mensagem) {
        super(mensagem);
    }

}
