
package com.ReFazer.back.end.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

// import com.ReFazer.back.end.dtos.req.ChangeAvaliacaoDTO;
// import com.ReFazer.back.end.dtos.req.ChangeTrabalhoSolicitadoDTO;
import com.ReFazer.back.end.dtos.req.ChangeUsuarioDTO;
// import com.ReFazer.back.end.dtos.req.CreateAvaliacaoDTO;
// import com.ReFazer.back.end.dtos.req.CreateTrabalhoSolicitadoDTO;
import com.ReFazer.back.end.dtos.req.CreateUsuarioDTO;
import com.ReFazer.back.end.dtos.resp.ShowAvaliacaoDTO;
import com.ReFazer.back.end.dtos.resp.ShowTrabalhoSolicitadoDTO;
import com.ReFazer.back.end.dtos.resp.ShowUsuarioDTO;
import com.ReFazer.back.end.entities.AvaliacaoEntity;
// import com.ReFazer.back.end.entities.AvaliacaoEntity;
import com.ReFazer.back.end.entities.TrabalhoSolicitadoEntity;
import com.ReFazer.back.end.entities.UsuarioEntity;
import com.ReFazer.back.end.exceptions.UsuarioNaoEncontradoException;
import com.ReFazer.back.end.repositories.AvaliacaoRepository;
import com.ReFazer.back.end.repositories.TrabalhoSolicitadoRepository;
import com.ReFazer.back.end.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    private String mensagemLogin;

    @Autowired

    UsuarioRepository usuarioRepository;

    @Autowired

    TrabalhoSolicitadoRepository trabalhoSolicitadoRepository;

    @Autowired

    AvaliacaoRepository avaliacaoRepository;

    @Transactional
    public UsuarioEntity createUsuario(CreateUsuarioDTO userDummy) {
        if (usuarioRepository.existsByEmail(userDummy.getEmail())) {
            throw new EmailJaCadastradoException("O e-mail já está cadastrado: " + userDummy.getEmail());
        }

        if (userDummy.getUsername() == null || userDummy.getUsername().isEmpty()) {
            throw new CampoObrigatorioException("O campo nome é obrigatório.");
        }

        if (userDummy.getEmail() == null || userDummy.getEmail().isEmpty()) {
            throw new CampoObrigatorioException("O campo email é obrigatório.");
        }

        if (userDummy.getPassword() == null || userDummy.getPassword().isEmpty()) {
            throw new CampoObrigatorioException("O campo senha é obrigatório.");
        }

        if (userDummy.getTelefone() == null || userDummy.getTelefone().isEmpty()) {
            throw new CampoObrigatorioException("O campo telefone é obrigatório.");
        }

        if (userDummy.getCep() == null || userDummy.getCep().isEmpty()) {
            throw new CampoObrigatorioException("O campo CEP é obrigatório.");
        }

        if (userDummy.getTipoUsuario() == null || userDummy.getTipoUsuario().isEmpty()) {
            throw new CampoObrigatorioException("O campo tipo de usuario é obrigatório.");
        }

        // Se todas as validações passaram, salva o usuário
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        // usuarioEntity.setId_usuario(dto.getId_usuario());
        // usuarioEntity.setEspecialidade(userDummy.getEspecialidade());
        usuarioEntity.setUsername(userDummy.getUsername());
        usuarioEntity.setEmail(userDummy.getEmail());
        usuarioEntity.setPassword(userDummy.getPassword());
        usuarioEntity.setTelefone(userDummy.getTelefone());
        usuarioEntity.setCep(userDummy.getCep());
        usuarioEntity.setTipoUsuario(userDummy.getTipoUsuario());

        usuarioEntity = usuarioRepository.save(usuarioEntity);

        // AvaliacaoEntity avaliacaoEntity = new AvaliacaoEntity();
        // avaliacaoEntity.setNota_avaliacao(dto.getAvaliacao().getNota_avaliacao());
        // avaliacaoEntity.setTexto_avaliativo(dto.getAvaliacao().getTexto_avaliativo());
        // avaliacaoEntity.setUsuario(usuarioEntity);

        // avaliacaoEntity = avaliacaoRepository.save(avaliacaoEntity);

        // List<TrabalhoSolicitadoEntity> trabalhosSolicitadoEntity = new ArrayList<>();

        // for (CreateTrabalhoSolicitadoDTO trabalhoSolicitadoDTO : dto.getTrabalhos())
        // {
        // TrabalhoSolicitadoEntity trabalhoSolicitadoEntity = new
        // TrabalhoSolicitadoEntity();
        // trabalhoSolicitadoEntity.setTipo(trabalhoSolicitadoDTO.getTipo());
        // trabalhoSolicitadoEntity.setValor(trabalhoSolicitadoDTO.getValor());
        // trabalhoSolicitadoEntity.setLocalizacao(trabalhoSolicitadoDTO.getLocalizacao());
        // trabalhoSolicitadoEntity.setDescricao(trabalhoSolicitadoDTO.getDescricao());
        // trabalhoSolicitadoEntity.setStatus(trabalhoSolicitadoDTO.isStatus());
        // trabalhoSolicitadoEntity.setUsuario(usuarioEntity);
        // trabalhoSolicitadoEntity.setCliente(usuarioEntity);
        // trabalhoSolicitadoEntity.setTrabalhador(usuarioEntity);

        // trabalhosSolicitadoEntity.add(trabalhoSolicitadoEntity);

        // }

        // trabalhoSolicitadoRepository.saveAll(trabalhosSolicitadoEntity);

        return usuarioEntity;
    }

    // @Transactional
    // public void createAvaliacao(CreateAvaliacaoDTO dto){

    // AvaliacaoEntity avaliacaoEntity = new AvaliacaoEntity();
    // avaliacaoEntity.setNota_avaliacao(dto.getNota_avaliacao());
    // avaliacaoEntity.setTexto_avaliativo(dto.getTexto_avaliativo());
    // avaliacaoEntity.setUsuario(dto.getUsuario());

    // avaliacaoEntity = avaliacaoRepository.save(avaliacaoEntity);

    // }

    public boolean loginUsuario(String email, String password) {
        // Busca o usuário pelo email
        UsuarioEntity usuario = usuarioRepository.findByEmail(email);

        // Verifica se o usuário não existe
        if (usuario == null) {
            mensagemLogin = "Conta não existe.";
            return false; // Retorna false se o usuário não existe
        }

        // // Verifica se a senha está incorreta
        // if (!usuario.getPassword().equals(password)) {
        // mensagemLogin = "Senha ou Email incorretos.";
        // return false; // Retorna false se a senha está incorreta
        // }

        // Caso o login seja bem-sucedido
        mensagemLogin = "Login bem-sucedido.";
        return true; // Retorna true se o login for bem-suced

    }

    public String getMensagemLogin() {
        return mensagemLogin; // Método para obter a mensagem de login
    }

    public List<ShowUsuarioDTO> getAllUsuarios() {
        List<UsuarioEntity> usuarioEntity = usuarioRepository.findAll();

        return usuarioEntity.stream()
                .map(usuario -> {
                    ShowUsuarioDTO usuarioDTO = new ShowUsuarioDTO();
                    List<ShowAvaliacaoDTO> avaliacoesDTO = new ArrayList<>();
                    List<ShowTrabalhoSolicitadoDTO> trabalhosSolicitadoDTO = new ArrayList<>();

                    for (TrabalhoSolicitadoEntity trabalhoSolicitadoEntity : usuario.getTrabalhos()) {
                        ShowTrabalhoSolicitadoDTO trabalhoSolicitadoDTO = new ShowTrabalhoSolicitadoDTO();
                        trabalhoSolicitadoDTO.setTipo(trabalhoSolicitadoEntity.getTipo());
                        trabalhoSolicitadoDTO.setValor(trabalhoSolicitadoEntity.getValor());
                        trabalhoSolicitadoDTO.setLocalizacao(trabalhoSolicitadoEntity.getLocalizacao());
                        trabalhoSolicitadoDTO.setDescricao(trabalhoSolicitadoEntity.getDescricao());
                        trabalhoSolicitadoDTO.setStatus(trabalhoSolicitadoEntity.isStatus());
                        trabalhosSolicitadoDTO.add(trabalhoSolicitadoDTO);
                    }

                    // Verifique se avaliacao não é nulo antes de acessar seus métodos
                    for( AvaliacaoEntity avalicaoEntity : usuario.getAvaliacao()){
                        ShowAvaliacaoDTO avaliacaoDTO = new ShowAvaliacaoDTO();
                        avaliacaoDTO.setId_trabalhador(avalicaoEntity.getTrabalhador().getId_Usuario());
                        avaliacaoDTO.setId_usuario(avalicaoEntity.getUsuario().getId_Usuario());
                        avaliacaoDTO.setNota_avaliacao(avalicaoEntity.getNota_avaliacao());
                        avaliacaoDTO.setTexto_avaliativo(avalicaoEntity.getTexto_avaliativo());
                        avaliacoesDTO.add(avaliacaoDTO);
                    }

                    usuarioDTO.setId_usuario(usuario.getId_Usuario());
                    usuarioDTO.setUsername(usuario.getUsernameFromEntity());
                    usuarioDTO.setEspecialidade(usuario.getEspecialidade());
                    usuarioDTO.setEmail(usuario.getEmail());
                    usuarioDTO.setPassword(usuario.getPassword());
                    usuarioDTO.setTelefone(usuario.getTelefone());
                    usuarioDTO.setCep(usuario.getCep());
                    usuarioDTO.setTipoUsuario(usuario.getTipoUsuario());
                    usuarioDTO.setTrabalhos(trabalhosSolicitadoDTO);
                    usuarioDTO.setAvaliacoes(avaliacoesDTO);

                    return usuarioDTO;
                }).toList();
    }

    @GetMapping("/trabalhadores")
    public List<ShowUsuarioDTO> getAllTrabalhadores() {

        List<UsuarioEntity> trabalhadores = usuarioRepository.findByTipoUsuario("TRABALHADOR");

        return trabalhadores.stream().map(trabalhador -> {

            ShowUsuarioDTO trabalhadorDto = new ShowUsuarioDTO();

            trabalhadorDto.setId_usuario(trabalhador.getId_Usuario());
            trabalhadorDto.setTelefone(trabalhador.getTelefone());
            trabalhadorDto.setUsername(trabalhador.getUsernameFromEntity());
            trabalhadorDto.setEspecialidade(trabalhador.getEspecialidade());
            trabalhadorDto.setCep(trabalhador.getCep());

            return trabalhadorDto;

        }).toList();
    }

    // public UsuarioEntity getUsuarioEntityById(Long id_usuario) {
    // return usuarioRepository.findById(id_usuario)
    // .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    // }

    public ShowUsuarioDTO getUsuarioById(Long id_usuario) {
        Optional<UsuarioEntity> optionalUsuarioEntity = usuarioRepository.findById(id_usuario);

        // Verifica se o usuário foi encontrado
        if (optionalUsuarioEntity.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        // Obtém a entidade do usuário
        UsuarioEntity usuarioEntity = optionalUsuarioEntity.get();
        ShowUsuarioDTO dto = new ShowUsuarioDTO();

        // Preenche os dados do DTO
        dto.setId_usuario(usuarioEntity.getId_Usuario());
        dto.setUsername(usuarioEntity.getUsernameFromEntity());
        dto.setEspecialidade(usuarioEntity.getEspecialidade());
        dto.setEmail(usuarioEntity.getEmail());
        dto.setPassword(usuarioEntity.getPassword());
        dto.setTelefone(usuarioEntity.getTelefone());
        dto.setCep(usuarioEntity.getCep());
        dto.setTipoUsuario(usuarioEntity.getTipoUsuario());
        
        List<ShowAvaliacaoDTO> avaliacoesDTO = new ArrayList<>();
        for(AvaliacaoEntity avaliacaoEntity : usuarioEntity.getAvaliacao()) {
            ShowAvaliacaoDTO avaliacaoDTO = new ShowAvaliacaoDTO();
            avaliacaoDTO.setId_avaliacao(avaliacaoEntity.getId_avaliacao());
            avaliacaoDTO.setId_trabalhador(avaliacaoEntity.getTrabalhador().getId_Usuario());
            avaliacaoDTO.setId_usuario(avaliacaoEntity.getUsuario().getId_Usuario());
            avaliacaoDTO.setNota_avaliacao(avaliacaoEntity.getNota_avaliacao());
            avaliacaoDTO.setTexto_avaliativo(avaliacaoEntity.getTexto_avaliativo());
            avaliacoesDTO.add(avaliacaoDTO);
        }

        // Preenche os dados dos trabalhos solicitados
        List<ShowTrabalhoSolicitadoDTO> trabalhosSolicitadosDTO = new ArrayList<>();
        for (TrabalhoSolicitadoEntity trabalho : usuarioEntity.getTrabalhos()) {
            ShowTrabalhoSolicitadoDTO trabalhoDTO = new ShowTrabalhoSolicitadoDTO();
            trabalhoDTO.setId_trabalho_solicitado(trabalho.getId_trabalho_solicitado());
            trabalhoDTO.setId_cliente(trabalho.getUsuario().getId_Usuario());
            trabalhoDTO.setId_trabalhador(trabalho.getTrabalhador().getId_Usuario());
            trabalhoDTO.setTipo(trabalho.getTipo());
            trabalhoDTO.setValor(trabalho.getValor());
            trabalhoDTO.setLocalizacao(trabalho.getLocalizacao());
            trabalhoDTO.setDescricao(trabalho.getDescricao());
            trabalhoDTO.setStatus(trabalho.isStatus());
            trabalhosSolicitadosDTO.add(trabalhoDTO);
        }

        // Adiciona os trabalhos solicitados ao DTO
        dto.setTrabalhos(trabalhosSolicitadosDTO);
        dto.setAvaliacoes(avaliacoesDTO);

        return dto;
    }

    // public void save(UsuarioEntity usuario) {
    // usuarioRepository.save(usuario);
    // }

    @Transactional
    public void deleteUsuarioById(long id_usuario) {

        // Buscar todos os trabalhos solicitados e avaliações
        List<TrabalhoSolicitadoEntity> trabalhos = trabalhoSolicitadoRepository.findAll();
        List<AvaliacaoEntity> avaliacoes = avaliacaoRepository.findAll();

        // Atualizar os trabalhos solicitados para remover as referências ao usuário
        for (int i = 0; i < trabalhos.size(); i++) {
            if (trabalhos.get(i).getUsuario().getId_Usuario().equals(id_usuario)) {
                System.out.println("Estou aqui e trabalho" + trabalhos.get(i).getId_trabalho_solicitado());
                trabalhos.get(i).setUsuario(null); // Remover referência ao usuário
                trabalhoSolicitadoRepository.deleteById(trabalhos.get(i).getId_trabalho_solicitado());
            }

            // Se o usuário for cliente de algum trabalho, remover também essa referência
            if (trabalhos.get(i).getTrabalhador() != null
                    && trabalhos.get(i).getTrabalhador().getId_Usuario().equals(id_usuario)) {
                trabalhos.get(i).setUsuario(null);
                trabalhoSolicitadoRepository.save(trabalhos.get(i)); // Salvar a atualização
                trabalhoSolicitadoRepository.deleteById(trabalhos.get(i).getId_trabalho_solicitado());
            }
        }

        // Remover as avaliações do usuário
        for (int i = 0; i < avaliacoes.size(); i++) {
            if (avaliacoes.get(i).getUsuario().getId_Usuario().equals(id_usuario)) {
                avaliacaoRepository.deleteById(avaliacoes.get(i).getId_avaliacao());
            }
        }

        // Finalmente, excluir o usuário
        usuarioRepository.deleteById(id_usuario);
    }

    

    // @Transactional
    // public void deleteAvaliacaoById(Long id_avaliacao) {

    // Optional<AvaliacaoEntity> optionalAvaliacaoEntity =
    // avaliacaoRepository.findById(id_avaliacao);

    // if (optionalAvaliacaoEntity.isEmpty()) {

    // }
    // AvaliacaoEntity avaliacaoEntity = optionalAvaliacaoEntity.get();

    // if (avaliacaoEntity != null) {
    // avaliacaoRepository.deleteById(id_avaliacao);
    // } else {

    // // throw new deletableException();

    // }

    // }

    // @Transactional
    // public void deleteTrabalhoSolicitadoById(Long id_trabalho_solicitado) {

    // Optional<TrabalhoSolicitadoEntity> optionalTrabalhoSolicitado =
    // trabalhoSolicitadoRepository
    // .findById(id_trabalho_solicitado);

    // if (optionalTrabalhoSolicitado.isEmpty()) {

    // }

    // TrabalhoSolicitadoEntity trabalhoSolicitadoEntity =
    // optionalTrabalhoSolicitado.get();

    // if (trabalhoSolicitadoEntity != null) {
    // trabalhoSolicitadoRepository.deleteById(id_trabalho_solicitado);

    // }
    // // throw new deletableException();

    // }
    @Transactional
    public void changeUsuarioInfosById(long id_usuario, ChangeUsuarioDTO dto) {
        System.out.println("Iniciando a atualização dos dados do usuário com ID: " + id_usuario);

        // Recupera o usuário opcional
        Optional<UsuarioEntity> optionalUsuarioEntity = usuarioRepository.findById(id_usuario);

        if (optionalUsuarioEntity.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }

        UsuarioEntity usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        // Verifica se a senha atual informada é correta
        if (dto.getPassword() != null && !usuario.getPassword().equals(dto.getPassword())) {
            throw new SenhaIncorretaException("Senha atual incorreta");
        }

        UsuarioEntity usuarioEntity = optionalUsuarioEntity.get();

        // Atualiza apenas os campos fornecidos no DTO
        if (dto.getEmail() != null) {
            usuarioEntity.setEmail(dto.getEmail());
        }

        if (dto.getNewPassword() != null) {
            usuarioEntity.setPassword(dto.getNewPassword());

        }
        if (dto.getTelefone() != null) {
            usuarioEntity.setTelefone(dto.getTelefone());
        }
        if (dto.getCep() != null) {
            usuarioEntity.setCep(dto.getCep());
        }
        if( dto.getUsername() != null) {
            usuarioEntity.setUsername(dto.getUsername());
        }
        // Apenas atualiza tipoUsuario se ele foi fornecido
        if (!isNullOrEmpty(dto.getTipoUsuario())) {
            usuarioEntity.setTipoUsuario(dto.getTipoUsuario());
        }

        System.out.println("Atualizando dados do usuário...");
        usuarioRepository.save(usuarioEntity);
        System.out.println("Dados do usuário atualizados com sucesso.");
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // @Transactional
    // public void changeAvaliacaoInfoByid(long id_avaliacao, ChangeAvaliacaoDTO
    // dto) {

    // Optional<AvaliacaoEntity> optionalAvaliacaoEntity =
    // avaliacaoRepository.findById(id_avaliacao);

    // if (optionalAvaliacaoEntity.isEmpty()) {

    // }

    // AvaliacaoEntity avaliacaoEntity = optionalAvaliacaoEntity.get();

    // avaliacaoEntity.setNota_avaliacao(dto.getNota_avaliacao());

    // avaliacaoEntity.setTexto_avaliativo(dto.getTexto_avaliativo());

    // avaliacaoRepository.save(avaliacaoEntity);

    // }

    // @Transactional
    // public void changeTrabalhoSolicitadoInfoById(long id_trabalho_solicitado,
    // ChangeTrabalhoSolicitadoDTO dto) {

    // Optional<TrabalhoSolicitadoEntity> optionalTrabalhoSolicitado =
    // trabalhoSolicitadoRepository
    // .findById((id_trabalho_solicitado));

    // if (optionalTrabalhoSolicitado.isEmpty()) {

    // }

    // TrabalhoSolicitadoEntity trabalhoSolicitadoEntity =
    // optionalTrabalhoSolicitado.get();

    // // Atualiza os campos da entidade a partir do DTO recebido
    // trabalhoSolicitadoEntity.setTipo(dto.getTipo());
    // trabalhoSolicitadoEntity.setValor(dto.getValor());
    // trabalhoSolicitadoEntity.setLocalizacao(dto.getLocalizacao());
    // trabalhoSolicitadoEntity.setDescricao(dto.getDescricao());
    // trabalhoSolicitadoEntity.setStatus(dto.isStatus());
    // trabalhoSolicitadoRepository.save(trabalhoSolicitadoEntity);

    // trabalhoSolicitadoRepository.save(trabalhoSolicitadoEntity);

    // }

}
