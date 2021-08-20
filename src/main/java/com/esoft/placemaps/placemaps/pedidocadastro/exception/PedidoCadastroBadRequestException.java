package com.esoft.placemaps.placemaps.pedidocadastro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PedidoCadastroBadRequestException extends RuntimeException {

    public PedidoCadastroBadRequestException(String mensagem) {
        super(mensagem);
    }

}
