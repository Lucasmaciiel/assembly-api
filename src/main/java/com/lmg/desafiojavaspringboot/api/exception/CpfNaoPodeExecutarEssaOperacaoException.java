package com.lmg.desafiojavaspringboot.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class CpfNaoPodeExecutarEssaOperacaoException extends RuntimeException {

    public CpfNaoPodeExecutarEssaOperacaoException(String message) {
        super(message);
    }
}
