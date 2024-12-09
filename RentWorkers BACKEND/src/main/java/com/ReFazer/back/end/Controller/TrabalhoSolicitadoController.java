package com.ReFazer.back.end.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ReFazer.back.end.dtos.req.ChangeStatusTrabalhoDTO;
// import com.ReFazer.back.end.dtos.req.ChangeAvaliacaoDTO;
import com.ReFazer.back.end.dtos.req.ChangeTrabalhoSolicitadoDTO;
import com.ReFazer.back.end.dtos.req.CreateTrabalhoSolicitadoDTO;
import com.ReFazer.back.end.dtos.resp.ShowTrabalhoSolicitadoDTO;
import com.ReFazer.back.end.entities.TrabalhoSolicitadoEntity;
import com.ReFazer.back.end.entities.UsuarioEntity;
import com.ReFazer.back.end.repositories.UsuarioRepository;
import com.ReFazer.back.end.services.TrabalhoSolicitadoService;

import io.micrometer.core.ipc.http.HttpSender.Response;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/trabalhos")
@CrossOrigin

public class TrabalhoSolicitadoController {

    @Autowired
    TrabalhoSolicitadoService trabalhoSolicitadoService;

    @Autowired

    UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> createTrabalhoSolicitado(@RequestBody CreateTrabalhoSolicitadoDTO dto) {
        
        if (dto.getTipo() == null || dto.getValor() == 0 || dto.getValor() <= 0 || dto.getDescricao() == null || dto.getLocalizacao() == null) {
            return ResponseEntity.badRequest().body("Campos obrigatórios não fornecidos ou valor inválido.");
        }
    

         System.out.println("Tipo: " + dto.getTipo());
         System.out.println("Valor: " + dto.getValor());
         System.out.println("Localização: " + dto.getLocalizacao());
         System.out.println("Descrição: " + dto.getDescricao());
         System.out.println("Status: " + dto.isStatus());

         if (dto.getId_cliente() != null) {
             UsuarioEntity cliente = usuarioRepository.findById(dto.getId_cliente())
                     .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
             trabalhoSolicitadoService.createTrabalhoSolicitado(dto, cliente);
         }

         return ResponseEntity.status(201).body("Solicitação de trabalho criada com sucesso.");

    }

    @GetMapping
    public ResponseEntity<?> getAllTrabalhoSolicitado(){

        List<ShowTrabalhoSolicitadoDTO> trabalhos = trabalhoSolicitadoService.getAllTrabalhoSolicitado();

        return ResponseEntity.status(200).body(trabalhos);
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<String> changeStatusTrabalho(@PathVariable Long id, @RequestBody ChangeStatusTrabalhoDTO dto) {
        try {
            // Chama o serviço para atualizar o status do trabalho
            trabalhoSolicitadoService.changeStatusTrabalhoById(id, dto);
            return ResponseEntity.ok("Status do trabalho atualizado com sucesso.");
        } catch (RuntimeException e) {
            // Se ocorrer algum erro (ex: trabalho não encontrado), retorna erro
            return ResponseEntity.status(404).body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("/trabalhador/{id_usuario}")
    public ResponseEntity<?> getTrabalhoByIdTrabalhador(@PathVariable Long id_usuario) {
        List<ShowTrabalhoSolicitadoDTO> trabalhosVerificados =  trabalhoSolicitadoService.getTrabalhosByIdTrabalhador(id_usuario);

        return ResponseEntity.status(200).body(trabalhosVerificados);
    }
    
    

    @PatchMapping("{id_trabalho_solicitado}")
    public ResponseEntity<?> changeTrabalhoSolicitado(@PathVariable long id_usuario,
            @PathVariable long id_trabalho_solicitado, @RequestBody ChangeTrabalhoSolicitadoDTO dto) {
        trabalhoSolicitadoService.changeTrabalhoSolicitadoInfoById(id_trabalho_solicitado, dto);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("{id_trabalho_solicitado}")

    public ResponseEntity<?> deleteTrabalhoSolicitado(@PathVariable Long id_trabalho_solicitado) {

        trabalhoSolicitadoService.deleteTrabalhoSolicitadoById(id_trabalho_solicitado);

        return ResponseEntity.status(200).build();

    }

    // @PatchMapping("/{id_usuario}/avaliacao/{id_avaliacao}")

    // public ResponseEntity<?> changeAValiacao(@PathVariable long id_avaliacao,
    // @RequestBody ChangeAvaliacaoDTO dto) {

    // avaliacaoService.changeAvaliacaoInfoByid(id_avaliacao, dto);

    // return ResponseEntity.status(200).build();

    // }


    @PatchMapping("{id_usuario}/trabalhos/{id_trabalho_solicitado}")
    public ResponseEntity<?> changeTrabalhoByid(@PathVariable long id_trabalho_solicitado,
            @RequestBody ChangeTrabalhoSolicitadoDTO dto) {

        trabalhoSolicitadoService.changeTrabalhoSolicitadoInfoById(id_trabalho_solicitado, dto);

        return ResponseEntity.status(200).build();
    }
}
