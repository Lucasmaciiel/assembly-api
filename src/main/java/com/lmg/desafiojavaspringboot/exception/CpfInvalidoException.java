package com.lmg.desafiojavaspringboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CpfInvalidoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CpfInvalidoException(String message) {
        super(message);
    }

}