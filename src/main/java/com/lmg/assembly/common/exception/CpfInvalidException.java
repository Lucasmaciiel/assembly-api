package com.lmg.assembly.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CpfInvalidException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CpfInvalidException(String message) {
        super(message);
    }

}