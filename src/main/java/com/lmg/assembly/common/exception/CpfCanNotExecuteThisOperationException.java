package com.lmg.assembly.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class CpfCanNotExecuteThisOperationException extends RuntimeException {

    public CpfCanNotExecuteThisOperationException(String message) {
        super(message);
    }
}
