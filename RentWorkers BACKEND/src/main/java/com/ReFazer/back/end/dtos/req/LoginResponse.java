package com.ReFazer.back.end.dtos.req;

import com.ReFazer.back.end.dtos.resp.ShowUsuarioDTO;

public class LoginResponse {
    private String token;

    private long expiresIn;

    private ShowUsuarioDTO usuario;

    

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public ShowUsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(ShowUsuarioDTO usuario) {
        this.usuario = usuario;
    }

    
}
