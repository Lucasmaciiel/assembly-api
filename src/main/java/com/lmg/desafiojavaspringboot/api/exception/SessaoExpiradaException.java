package com.lmg.desafiojavaspringboot.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
public class SessaoExpiradaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SessaoExpiradaException(String message) {
        super(message);
    }
}
