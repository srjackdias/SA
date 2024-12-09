package com.ReFazer.back.end.dtos.req;

import com.ReFazer.back.end.entities.UsuarioEntity;

public class CreateAvaliacaoDTO {
   
    private double nota_avaliacao;
    private String texto_avaliativo;
    
    private Long id_trabalhador;
    private Long id_usuario;

    public double getNota_avaliacao() {
        return nota_avaliacao;
    }

    public void setNota_avaliacao(double nota_avaliacao) {
        this.nota_avaliacao = nota_avaliacao;
    }

    public String getTexto_avaliativo() {
        return texto_avaliativo;
    }

    public void setTexto_avaliativo(String texto_avaliativo) {
        this.texto_avaliativo = texto_avaliativo;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getId_trabalhador() {
        return id_trabalhador;
    }

    public void setId_trabalhador(Long id_trabalhador) {
        this.id_trabalhador = id_trabalhador;
    }

}
