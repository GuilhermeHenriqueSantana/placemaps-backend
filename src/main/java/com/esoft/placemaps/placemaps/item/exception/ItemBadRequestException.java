package com.esoft.placemaps.placemaps.item.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ItemBadRequestException extends RuntimeException{
    
    public ItemBadRequestException(String mensagem) {
        super(mensagem);
    }

}
