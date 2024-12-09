package com.ReFazer.back.end.services;

public class SenhaIncorretaException extends RuntimeException {

    // Construtor sem parâmetros
    public SenhaIncorretaException() {
        super("Senha atual incorreta");
    }

    // Construtor que permite passar uma mensagem personalizada
    public SenhaIncorretaException(String message) {
        super(message);
    }

    // Construtor que permite passar a mensagem e a causa da exceção
    public SenhaIncorretaException(String message, Throwable cause) {
        super(message, cause);
    }

    // Construtor que permite passar apenas a causa
    public SenhaIncorretaException(Throwable cause) {
        super(cause);
    }
}