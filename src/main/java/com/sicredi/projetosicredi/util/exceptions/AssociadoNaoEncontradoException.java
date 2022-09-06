package com.sicredi.projetosicredi.util.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AssociadoNaoEncontradoException extends RuntimeException {
    public AssociadoNaoEncontradoException(String ex) { super(ex); }
}
