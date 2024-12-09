package com.ReFazer.back.end.dtos.req;

public class CreateTrabalhoSolicitadoDTO {
    private String tipo;
    private float valor;
    private String localizacao;
    private String descricao;

    private Long id_usuario;

    private Long id_cliente; // Pode ser nulo
    private Long id_trabalhador; // Pode ser nulo

    private boolean status;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Long getId_trabalhador() {
        return id_trabalhador;
    }

    public void setId_trabalhador(Long id_trabalhador) {
        this.id_trabalhador = id_trabalhador;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
}