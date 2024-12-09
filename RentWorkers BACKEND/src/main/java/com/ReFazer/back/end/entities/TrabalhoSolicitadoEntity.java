package com.ReFazer.back.end.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "trabalho_solicitado")
public class TrabalhoSolicitadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_trabalho_solicitado")

    private Long id_trabalho_solicitado;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "valor")
    private float valor;

    @Column(name = "localizacao")
    private String localizacao;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "status")
    private boolean status;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = true) 
    private UsuarioEntity cliente;

    @ManyToOne
    @JoinColumn(name = "id_trabalhador", nullable = true) 
    private UsuarioEntity trabalhador;

    public Long getId_trabalho_solicitado() {
        return id_trabalho_solicitado;
    }

    public void setId_trabalho_solicitado(Long id_trabalho_solicitado) {
        this.id_trabalho_solicitado = id_trabalho_solicitado;
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

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public UsuarioEntity getCliente() {
        return cliente;
    }

    public void setCliente(UsuarioEntity cliente) {
        this.cliente = cliente;
    }

    public UsuarioEntity getTrabalhador() {
        return trabalhador;
    }

    public void setTrabalhador(UsuarioEntity trabalhador) {
        this.trabalhador = trabalhador;
    }

}
