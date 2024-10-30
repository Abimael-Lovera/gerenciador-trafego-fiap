package com.fiap.exceptions;

public class ClimaNaoEncontradaException extends RuntimeException {
    public ClimaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
