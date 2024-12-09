package com.ReFazer.back.end.Controller;

import javax.naming.AuthenticationException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ReFazer.back.end.dtos.req.LoginResponse;
import com.ReFazer.back.end.dtos.req.LoginUserDto;
import com.ReFazer.back.end.dtos.req.RegisterUserDto;
import com.ReFazer.back.end.dtos.resp.ShowUsuarioDTO;
import com.ReFazer.back.end.entities.UsuarioEntity;
import com.ReFazer.back.end.repositories.UsuarioRepository;
import com.ReFazer.back.end.services.AuthenticationService;
import com.ReFazer.back.end.services.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    @Autowired
    private UsuarioRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UsuarioEntity> register(@RequestBody RegisterUserDto registerUserDto) {
        UsuarioEntity registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) throws AuthenticationException {
    // Injeção de dependência do repositório, encoder de senha e serviço JWT

    // 1. Buscar o usuário no banco de dados
    UsuarioEntity usuario = userRepository.findByEmail(loginUserDto.getEmail());

    // 2. Verificar se o usuário foi encontrado
    if (usuario == null) {
        throw new AuthenticationException("Usuário não encontrado");
    }

    // 3. Verificar se a senha fornecida corresponde ao hash da senha armazenada
    boolean isPasswordValid = passwordEncoder.matches(loginUserDto.getPassword(), usuario.getPassword());

    if (!isPasswordValid) {
        throw new AuthenticationException("Senha inválida");
    }

    // 4. Gerar o token JWT
    String jwtToken = jwtService.generateToken(usuario);

    // 5. Criar a resposta de login
    LoginResponse response = new LoginResponse();
    response.setToken(jwtToken);
    response.setExpiresIn(jwtService.getExpirationTime());

    // 6. Criar o DTO com os dados do usuário para a resposta
    ShowUsuarioDTO showUsuarioDTO = new ShowUsuarioDTO();
    showUsuarioDTO.setCep(usuario.getCep());
    showUsuarioDTO.setEmail(usuario.getEmail());
    showUsuarioDTO.setTelefone(usuario.getTelefone());
    showUsuarioDTO.setId_usuario(usuario.getId_Usuario());
    showUsuarioDTO.setTipoUsuario(usuario.getTipoUsuario());
    showUsuarioDTO.setUsername(usuario.getUsername());

    response.setUsuario(showUsuarioDTO);

    // 7. Retornar a resposta com status OK
    return ResponseEntity.ok(response);
}

}