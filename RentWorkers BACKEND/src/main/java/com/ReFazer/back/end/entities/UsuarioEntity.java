package com.ReFazer.back.end.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity(name = "usuario")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id_Usuario;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "especialidade")
    private String especialidade;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cep")
    private String cep;

    @Column(name = "tipo_usuario", nullable = false)
    private String tipoUsuario;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<AvaliacaoEntity> avaliacao;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<TrabalhoSolicitadoEntity> trabalhos;

    // Implementação de métodos da interface UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(tipoUsuario));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // O login será feito pelo email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Getter para o username real
    public String getUsernameFromEntity() {
        return username;
    }

    // Getters e setters
    public Long getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(Long id_Usuario) {
        this.id_Usuario = id_Usuario;
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

    public void setPassword(String password) {
        this.password = password;
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

    

    public List<TrabalhoSolicitadoEntity> getTrabalhos() {
        return trabalhos;
    }

    public void setTrabalhos(List<TrabalhoSolicitadoEntity> trabalhos) {
        this.trabalhos = trabalhos;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<AvaliacaoEntity> getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(List<AvaliacaoEntity> avaliacao) {
        this.avaliacao = avaliacao;
    }
}
