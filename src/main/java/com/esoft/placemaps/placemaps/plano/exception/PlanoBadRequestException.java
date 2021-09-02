package com.esoft.placemaps.placemaps.plano.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PlanoBadRequestException extends RuntimeException {

  public PlanoBadRequestException(String mensagem) {
    super(mensagem);
  }

}
