package com.ReFazer.back.end.services;

public class CampoObrigatorioException extends RuntimeException {
    public CampoObrigatorioException(String message) {
        super(message);
    }
}