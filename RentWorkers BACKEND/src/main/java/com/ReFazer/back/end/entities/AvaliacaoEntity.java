package com.ReFazer.back.end.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity(name = "avaliacao")
public class AvaliacaoEntity {
    


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_avaliacao")

    private Long id_avaliacao;


    @Column(name = "nota_avaliacao")
    private double nota_avaliacao;

    @Column(name = "texto_avaliativo")
    private String texto_avaliativo;


    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "id_trabalhador", nullable = true) 
    private UsuarioEntity trabalhador;
    

    public Long getId_avaliacao() {
        return id_avaliacao;
    }


    public UsuarioEntity getTrabalhador() {
        return trabalhador;
    }


    public void setTrabalhador(UsuarioEntity trabalhador) {
        this.trabalhador = trabalhador;
    }


    public void setId_avaliacao(Long id_avaliacao) {
        this.id_avaliacao = id_avaliacao;
    }


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


    public UsuarioEntity getUsuario() {
        return usuario;
    }


    public void setUsuario(UsuarioEntity usuarioEntity) {
        this.usuario = usuarioEntity;
    }


    
}
