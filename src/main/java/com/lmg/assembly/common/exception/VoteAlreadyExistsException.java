package com.lmg.assembly.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class VoteAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VoteAlreadyExistsException(String message) {
        super(message);
    }
}
