package com.esoft.placemaps.placemaps.ponto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PontoBadRequestException extends RuntimeException {

  public PontoBadRequestException(String mensagem) {
    super(mensagem);
  }

}
