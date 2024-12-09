package com.ReFazer.back.end.dtos.resp;

import java.util.List;


public class ShowUsuarioDTO {
    

    private Long id_usuario;
    private String username;
    private String especialidade;

    private String email;
    private String password;


    private String senha;
    private String telefone;
    private String cep;
    private String tipoUsuario;


    private List<ShowAvaliacaoDTO> avaliacoes;
    private List<ShowTrabalhoSolicitadoDTO> trabalhos;
    public Long getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEspecialidade() {
        return especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getTipoUsuario() {
        return tipoUsuario;
    }
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    public List<ShowTrabalhoSolicitadoDTO> getTrabalhos() {
        return trabalhos;
    }
    public void setTrabalhos(List<ShowTrabalhoSolicitadoDTO> trabalhos) {
        this.trabalhos = trabalhos;
    }
    public List<ShowAvaliacaoDTO> getAvaliacoes() {
        return avaliacoes;
    }
    public void setAvaliacoes(List<ShowAvaliacaoDTO> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }
   

    
}
