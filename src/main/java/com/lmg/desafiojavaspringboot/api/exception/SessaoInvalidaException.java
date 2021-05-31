package com.lmg.desafiojavaspringboot.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SessaoInvalidaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SessaoInvalidaException(String message) {
        super(message);
    }

}
