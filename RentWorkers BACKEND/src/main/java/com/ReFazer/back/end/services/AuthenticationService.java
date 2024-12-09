package com.ReFazer.back.end.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ReFazer.back.end.dtos.req.LoginUserDto;
import com.ReFazer.back.end.dtos.req.RegisterUserDto;
import com.ReFazer.back.end.entities.UsuarioEntity;
import com.ReFazer.back.end.repositories.UserRepository;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioEntity signup(RegisterUserDto input) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername(input.getUsername());
        usuario.setEspecialidade(input.getEspecialidade());
        usuario.setEmail(input.getEmail());
        usuario.setCep(input.getCep());
        usuario.setTelefone(input.getTelefone());
        usuario.setTipoUsuario(input.getTipoUsuario());

        usuario.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(usuario);
    }

    public UsuarioEntity authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();

                
    }
}