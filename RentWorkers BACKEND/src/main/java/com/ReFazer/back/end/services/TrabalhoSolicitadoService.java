package com.ReFazer.back.end.services;

import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ReFazer.back.end.dtos.req.ChangeStatusTrabalhoDTO;
import com.ReFazer.back.end.dtos.req.ChangeTrabalhoSolicitadoDTO;
import com.ReFazer.back.end.dtos.req.CreateTrabalhoSolicitadoDTO;
import com.ReFazer.back.end.dtos.resp.ShowTrabalhoSolicitadoDTO;
import com.ReFazer.back.end.entities.TrabalhoSolicitadoEntity;
import com.ReFazer.back.end.entities.UsuarioEntity;
import com.ReFazer.back.end.repositories.TrabalhoSolicitadoRepository;
import com.ReFazer.back.end.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class TrabalhoSolicitadoService {

    @Autowired

    TrabalhoSolicitadoRepository trabalhoSolicitadoRepository;

    @Autowired

    UsuarioRepository usuarioRepository;

    @Transactional
    public void createTrabalhoSolicitado(CreateTrabalhoSolicitadoDTO dto, UsuarioEntity cliente) {
        TrabalhoSolicitadoEntity trabalho = new TrabalhoSolicitadoEntity();

        trabalho.setTipo(dto.getTipo()); 
        trabalho.setValor(dto.getValor());
        trabalho.setDescricao(dto.getDescricao()); 

        
        trabalho.setLocalizacao(dto.getLocalizacao());

        trabalho.setStatus(dto.isStatus()); 

        
        if (dto.getId_cliente() != null) {
           
            UsuarioEntity clienteEntity = usuarioRepository.findById(dto.getId_cliente())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            trabalho.setCliente(clienteEntity);
        } else {
            throw new RuntimeException("ID do cliente não fornecido no DTO");
        }

        
        if (dto.getId_trabalhador() != null) {
            UsuarioEntity trabalhador = usuarioRepository.findById(dto.getId_trabalhador())
                    .orElseThrow(() -> new RuntimeException("Trabalhador não encontrado"));
            trabalho.setTrabalhador(trabalhador); 
        } else {
            throw new RuntimeException("ID do trabalhador não fornecido no DTO");
        }

     
        if (cliente != null) {
            trabalho.setUsuario(cliente); 
        } else {
            throw new RuntimeException("Cliente não fornecido no parâmetro");
        }

        // Salvar a entidade TrabalhoSolicitado no repositório
        trabalhoSolicitadoRepository.save(trabalho);
    }

    @Transactional
    public List<ShowTrabalhoSolicitadoDTO> getTrabalhosByIdTrabalhador(long id_usuario) {
        List<TrabalhoSolicitadoEntity> trabalhos = trabalhoSolicitadoRepository.findAll();
        
        List<ShowTrabalhoSolicitadoDTO> trabalhosFiltrados = new ArrayList<>();
        
        for(int i = 0; i < trabalhos.size(); i++) {
            if(trabalhos.get(i).getTrabalhador().getId_Usuario().equals(id_usuario)) {        
                ShowTrabalhoSolicitadoDTO trabalho = new ShowTrabalhoSolicitadoDTO();
                trabalho.setId_trabalho_solicitado(trabalhos.get(i).getId_trabalho_solicitado());
                trabalho.setId_trabalhador(trabalhos.get(i).getTrabalhador().getId_Usuario());
                trabalho.setId_cliente(trabalhos.get(i).getUsuario().getId_Usuario());
                trabalho.setTipo(trabalhos.get(i).getTipo());
                trabalho.setDescricao(trabalhos.get(i).getDescricao());
                trabalho.setValor(trabalhos.get(i).getValor());
                trabalho.setLocalizacao(trabalhos.get(i).getLocalizacao());
                trabalho.setStatus(trabalhos.get(i).isStatus());
                System.out.print(trabalho.getId_trabalho_solicitado());
                trabalhosFiltrados.add(trabalho);
            }
        }
        
        return trabalhosFiltrados;
    }

    // public void postaTrabalhoAoTrabalhador( CreateTrabalhoSolicitadoDTO dto ) { 
    //     List<TrabalhoSolicitadoEntity> trabalho = trabalhoSolicitadoRepository.findAll();

    //     for (int i = 0; i < trabalho.size(); i++ ) {
    //        if(trabalho.get(i).getTrabalhador().getId_Usuario().equals(dto.getId_trabalhador())){
    //          trabalho.get(i).setUsuario();
    //        }
    //     }
        
        
    // }

    public List<ShowTrabalhoSolicitadoDTO> getAllTrabalhoSolicitado(){

        List<ShowTrabalhoSolicitadoDTO> trabalhos = new ArrayList<>();

        for(TrabalhoSolicitadoEntity trabalhoSolicitado : trabalhoSolicitadoRepository.findAll()){
            ShowTrabalhoSolicitadoDTO trabalhoSolicitadoDTO = new ShowTrabalhoSolicitadoDTO();

            trabalhoSolicitadoDTO.setTipo(trabalhoSolicitado.getTipo());
            trabalhoSolicitadoDTO.setValor(trabalhoSolicitado.getValor());
            trabalhoSolicitadoDTO.setLocalizacao(trabalhoSolicitado.getLocalizacao());
            trabalhoSolicitadoDTO.setDescricao(trabalhoSolicitado.getDescricao());
            trabalhoSolicitadoDTO.setStatus(trabalhoSolicitado.isStatus());

            trabalhos.add(trabalhoSolicitadoDTO);



        }

        return trabalhos;


    }



    public void changeStatusTrabalhoById(Long id, ChangeStatusTrabalhoDTO dto) {
        // Buscar o trabalho solicitado pelo ID
        Optional<TrabalhoSolicitadoEntity> trabalhoSolicitadoEntityOptional = trabalhoSolicitadoRepository.findById(id);

        if (trabalhoSolicitadoEntityOptional.isPresent()) {
            TrabalhoSolicitadoEntity trabalhoSolicitadoEntity = trabalhoSolicitadoEntityOptional.get();
            // Alterando o status para 'true'
            trabalhoSolicitadoEntity.setStatus(true);
            // Salvando a alteração no banco de dados
            trabalhoSolicitadoRepository.save(trabalhoSolicitadoEntity);
        } else {
            throw new RuntimeException("Trabalho solicitado não encontrado com o ID fornecido.");
        }
    }

    @Transactional
    public void deleteTrabalhoSolicitadoById(Long id_trabalho_solicitado) {

        Optional<TrabalhoSolicitadoEntity> optionalTrabalhoSolicitado = trabalhoSolicitadoRepository
            .findById(id_trabalho_solicitado);

        TrabalhoSolicitadoEntity trabalhoSolicitadoEntity = optionalTrabalhoSolicitado.get();

        if (trabalhoSolicitadoEntity != null) {
            trabalhoSolicitadoRepository.deleteById(id_trabalho_solicitado);
        }
    
    }

    @Transactional
    public void changeTrabalhoSolicitadoInfoById(long id_trabalho_solicitado, ChangeTrabalhoSolicitadoDTO dto) {

        Optional<TrabalhoSolicitadoEntity> optionalTrabalhoSolicitado = trabalhoSolicitadoRepository
                .findById((id_trabalho_solicitado));

        if (optionalTrabalhoSolicitado.isEmpty()) {

        }

        TrabalhoSolicitadoEntity trabalhoSolicitadoEntity = optionalTrabalhoSolicitado.get();

        // Atualiza os campos da entidade a partir do DTO recebido
        trabalhoSolicitadoEntity.setTipo(dto.getTipo());
        trabalhoSolicitadoEntity.setValor(dto.getValor());
        trabalhoSolicitadoEntity.setLocalizacao(dto.getLocalizacao());
        trabalhoSolicitadoEntity.setDescricao(dto.getDescricao());
        trabalhoSolicitadoEntity.setStatus(dto.isStatus());
        trabalhoSolicitadoRepository.save(trabalhoSolicitadoEntity);

        trabalhoSolicitadoRepository.save(trabalhoSolicitadoEntity);

    }

}
