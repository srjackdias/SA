package com.ReFazer.back.end.dtos.resp;

public class ShowTrabalhoSolicitadoDTO {
    private String tipo;
    private float valor;
    private String localizacao;
    private String descricao;
    private boolean status;
    private long id_trabalhador;
    private long id_cliente;
    private long id_trabalho_solicitado;
    
    public long getId_trabalhador() {
        return id_trabalhador;
    }
    public long getId_trabalho_solicitado() {
        return id_trabalho_solicitado;
    }
    public void setId_trabalho_solicitado(long id_trabalho_solicitado) {
        this.id_trabalho_solicitado = id_trabalho_solicitado;
    }
    public void setId_trabalhador(long id_trabalhador) {
        this.id_trabalhador = id_trabalhador;
    }
    public long getId_cliente() {
        return id_cliente;
    }
    public void setId_cliente(long id_cliente) {
        this.id_cliente = id_cliente;
    }
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
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    
}
