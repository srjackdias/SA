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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ReFazer.back.end.dtos.req.ChangeAvaliacaoDTO;
import com.ReFazer.back.end.dtos.req.CreateAvaliacaoDTO;
import com.ReFazer.back.end.dtos.resp.ShowAvaliacaoDTO;
import com.ReFazer.back.end.dtos.resp.ShowUsuarioDTO;
import com.ReFazer.back.end.services.AvaliacaoService;
import com.ReFazer.back.end.services.UsuarioService;

@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin
public class AvaliacaoController {

    @Autowired
    AvaliacaoService avaliacaoService;

    @PostMapping
    public ResponseEntity<?> createAvaliacao(@RequestBody CreateAvaliacaoDTO dto) {

        System.out.println(dto.getNota_avaliacao());
        System.out.println(dto.getTexto_avaliativo());

        avaliacaoService.createAvaliacao(dto);

        return ResponseEntity.status(201).build();

    }

    @GetMapping
    public ResponseEntity<?> getAllAvaliacoes() {

        List<ShowAvaliacaoDTO> avaliacao = avaliacaoService.getAllAvaliacoes();

        return ResponseEntity.status(200).body(avaliacao);

    }

    @GetMapping("/trabalhador/{id_usuario}")
    public ResponseEntity<?> getAvaliacaoIdTrabalhador(@PathVariable Long id_usuario) {
        List<ShowAvaliacaoDTO> avaliacoes = avaliacaoService.getAvaliacaoByIdTrabalhador(id_usuario);

        return ResponseEntity.status(200).body(avaliacoes);
    }

    @DeleteMapping("{id_avaliacao}")

    public ResponseEntity<?> deleteAvaliacao(@PathVariable Long id_avaliacao) {

        avaliacaoService.deleteAvaliacaoById(id_avaliacao);

        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/{id_usuario}/avaliacao/{id_avaliacao}")

    public ResponseEntity<?> changeAValiacao(@PathVariable long id_avaliacao, @RequestBody ChangeAvaliacaoDTO dto) {

        avaliacaoService.changeAvaliacaoInfoByid(id_avaliacao, dto);

        return ResponseEntity.status(200).build();

    }

}
