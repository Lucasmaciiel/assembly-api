package com.lmg.desafiojavaspringboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class VotoJaExisteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VotoJaExisteException(String message) {
        super(message);
    }
}
