package com.esoft.placemaps.placemaps.avaliacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AvaliacaoBadRequestException extends RuntimeException {

    public AvaliacaoBadRequestException(String mensagem) {
        super(mensagem);
    }

}
