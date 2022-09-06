package com.sicredi.projetosicredi.util.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AssociadoExistenteException extends RuntimeException {

    public AssociadoExistenteException(String ex) {
        super(ex);
    }
}
