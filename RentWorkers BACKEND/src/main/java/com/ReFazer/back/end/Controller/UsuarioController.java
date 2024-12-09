package com.ReFazer.back.end.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.ReFazer.back.end.dtos.req.ChangeAvaliacaoDTO;
import com.ReFazer.back.end.dtos.req.ChangeUsuarioDTO;
// import com.ReFazer.back.end.dtos.req.CreateAvaliacaoDTO;
import com.ReFazer.back.end.dtos.req.CreateUsuarioDTO;
import com.ReFazer.back.end.dtos.resp.ShowUsuarioDTO;
// import com.ReFazer.back.end.services.TrabalhoSolicitadoService;
import com.ReFazer.back.end.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")

@CrossOrigin
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;



    // @Autowired
    // TrabalhoSolicitadoService trabalhoSolicitadoService;

    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody CreateUsuarioDTO dto) {
        // System.out.println(dto.getId_usuario());

        System.out.println(dto.getUsername());
        System.out.println(dto.getEmail());
        System.out.println(dto.getPassword());
        System.out.println(dto.getTelefone());
        System.out.println(dto.getCep());
        System.out.println(dto.getTipoUsuario());

        // System.out.println(dto.getAvaliacao().getNota_avaliacao());
        // System.out.println(dto.getAvaliacao().getTexto_avaliativo());

        // System.out.println(dto.getTrabalhos().get(0).getTipo());
        // System.out.println(dto.getTrabalhos().get(0).getValor());
        // System.out.println(dto.getTrabalhos().get(0).getLocalizacao());
        // System.out.println(dto.getTrabalhos().get(0).getDescricao());
        // System.out.println(dto.getTrabalhos().get(0).isStatus());

        usuarioService.createUsuario(dto);

        return ResponseEntity.status(201).build();
    }

    // @PostMapping("avaliacao")
    // public ResponseEntity<?> createAvaliacao(@RequestBody CreateAvaliacaoDTO dto){

    //     System.out.println(dto.getNota_avaliacao());
    //     System.out.println(dto.getTexto_avaliativo());

    //     usuarioService.createAvaliacao(dto);

    //     return ResponseEntity.status(201).build();

    // }



    @GetMapping
    public ResponseEntity<?> getAllUsuarios() {

        List<ShowUsuarioDTO> usuarios = usuarioService.getAllUsuarios();

        return ResponseEntity.status(200).body(usuarios);
    }
    
    @GetMapping("/trabalhadores")
    public ResponseEntity<?> getAllTrabalhadores() {
        List<ShowUsuarioDTO> trabalhadores = usuarioService.getAllTrabalhadores();

        return ResponseEntity.status(200).body(trabalhadores);
    }

    @GetMapping("/{id_usuario}")
    public ResponseEntity<?> getAllUsuarios(@PathVariable long id_usuario) {

        System.out.println(id_usuario);
        ShowUsuarioDTO usuario = usuarioService.getUsuarioById(id_usuario);

        return ResponseEntity.status(200).body(usuario);

    }

    @PatchMapping("/{id_usuario}")
    public ResponseEntity<?> changeUsuario(@PathVariable long id_usuario, @RequestBody ChangeUsuarioDTO   dto) {
        System.out.println("TEste");
        usuarioService.changeUsuarioInfosById(id_usuario, dto);

        return ResponseEntity.ok(dto);

    }

    // @PatchMapping("/{id_usuario}/avaliacao/{id_avaliacao}")

    // public ResponseEntity<?> changeAValiacao(@PathVariable long id_avaliacao, @RequestBody ChangeAvaliacaoDTO   dto) {

    //     usuarioService.changeAvaliacaoInfoByid(id_avaliacao, dto);

    //     return ResponseEntity.status(200).build();

    // }

    // @PatchMapping("/{id_usuario}/trabalho/{id_trabalho_solicitado}")
    // public ResponseEntity<?> changeTrabalhoSolicitado(@PathVariable long id_usuario, @PathVariable long id_trabalho_solicitado, @RequestBody ChangeTrabalhoSolicitadoDTO dto) {
    //     usuarioService.changeTrabalhoSolicitadoInfoById(id_trabalho_solicitado, dto);
    //     return ResponseEntity.status(200).build();
    // }

    @DeleteMapping("/{id_usuario}")

    public ResponseEntity<?> deleteUsuario(@PathVariable Long id_usuario){

        usuarioService.deleteUsuarioById(id_usuario);

        return ResponseEntity.status(200).build();
    }


    // @DeleteMapping("/avaliacao/{id_avaliacao}")

    // public ResponseEntity<?> deleteAvaliacao(@PathVariable Long id_avaliacao){

    //     usuarioService.deleteAvaliacaoById(id_avaliacao);

    //     return ResponseEntity.status(200).build();
    // }



    // @DeleteMapping("/trabalhos/{id_trabalho_solicitado}")

    //  public ResponseEntity<?> deleteTrabalhoSolicitado(@PathVariable Long id_trabalho_solicitado){

    //     usuarioService.deleteTrabalhoSolicitadoById(id_trabalho_solicitado);

    //     return ResponseEntity.status(200).build();




    //  }

  
    // @PutMapping("/{id_usuario}/trabalho/{id_trabalho_solicitado}")
    // public ResponseEntity<?> putMethodName(@PathVariable long id_usuario, @PathVariable String tipoProjeto,
    //         @RequestBody ChangeTrabalhoSolicitadoDTO changeTrabalhoSolicitadoDTO) {

    //     ShowUsuarioDTO usuaDTO = usuarioService.getUsuarioById(id_usuario);

    //     for (ShowTrabalhoSolicitadoDTO trabalhoSolicitadoDTO : usuaDTO.getTrabalhos()) {
    //         if (trabalhoSolicitadoDTO.getTipo().equals(tipoProjeto)) {

    //             trabalhoSolicitadoDTO.setTipo(changeTrabalhoSolicitadoDTO.getTipo());
    //             trabalhoSolicitadoDTO.setValor(changeTrabalhoSolicitadoDTO.getValor());
    //             trabalhoSolicitadoDTO.setLocalizacao(changeTrabalhoSolicitadoDTO.getLocalizacao());
    //             trabalhoSolicitadoDTO.setDescricao(changeTrabalhoSolicitadoDTO.getDescricao());
    //             trabalhoSolicitadoDTO.setStatus(changeTrabalhoSolicitadoDTO.isStatus());
    //         }

    //     }
    //     return ResponseEntity.status(200).build();

    // }
    @PutMapping("/{id_usuario}")
    public ResponseEntity<?> updateUsuario(@PathVariable long id_usuario, @RequestBody ChangeUsuarioDTO changeUsuarioDTO) {
        System.out.println("Recebendo requisição para atualizar usuário com ID: " + id_usuario);
    
        // Verificando se o token foi enviado e se a autenticação está funcionando
        // Aqui você pode adicionar uma lógica para verificar o token de autenticação
        String token = "Seu mecanismo de recuperação de token, caso necessário";
        if (token == null || token.isEmpty()) {
            System.out.println("Token não fornecido.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token de autenticação é necessário.");
        } else {
            System.out.println("Token recebido: " + token);
            // Se houver algum processo de validação de token, adicione aqui
        }
    
        // Chama o serviço de atualização
        try {
            usuarioService.changeUsuarioInfosById(id_usuario, changeUsuarioDTO);
            System.out.println("Usuário atualizado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o usuário.");
        }
    
        if (changeUsuarioDTO.getTipoUsuario() == null) {
            System.out.println("Erro: 'tipoUsuario' é obrigatório.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo tipo_usuario não pode ser nulo");
        }
    
        return ResponseEntity.ok(changeUsuarioDTO);
    }
    

    

 
}
