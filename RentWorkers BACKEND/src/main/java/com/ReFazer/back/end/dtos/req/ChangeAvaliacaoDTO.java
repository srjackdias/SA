package com.ReFazer.back.end.dtos.req;

public class ChangeAvaliacaoDTO {
    private double nota_avaliacao;
    private String texto_avaliativo;

    
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

    
}
