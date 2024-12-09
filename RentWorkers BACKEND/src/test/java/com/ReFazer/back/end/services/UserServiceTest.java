package com.ReFazer.back.end.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ReFazer.back.end.dtos.req.ChangeUsuarioDTO;
import com.ReFazer.back.end.dtos.req.CreateTrabalhoSolicitadoDTO;
import com.ReFazer.back.end.dtos.req.CreateUsuarioDTO;
import com.ReFazer.back.end.dtos.resp.ShowTrabalhoSolicitadoDTO;
import com.ReFazer.back.end.entities.TrabalhoSolicitadoEntity;
import com.ReFazer.back.end.entities.UsuarioEntity;
import com.ReFazer.back.end.repositories.UserRepository;
import com.ReFazer.back.end.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TrabalhoSolicitadoService trabalhoSolicitadoService;

    @Test
    public void testCriarUsuarioComSucesso() {
        CreateUsuarioDTO userDummy = new CreateUsuarioDTO();
        userDummy.setUsername("jackson");
        userDummy.setEmail("sr.jackdias@gmail.com");
        userDummy.setPassword("1234");
        userDummy.setTelefone("12345");
        userDummy.setCep("88049317");
        userDummy.setTipoUsuario("cliente");

        UsuarioEntity usuarioCriado = usuarioService.createUsuario(userDummy);

        // Verificando se os dados foram salvos corretamente
        assertNotNull(usuarioCriado);
        assertNotNull(usuarioCriado.getId_Usuario());
        assertEquals(userDummy.getUsername(), usuarioCriado.getUsernameFromEntity());
        assertEquals(userDummy.getEmail(), usuarioCriado.getEmail());
        assertEquals(userDummy.getPassword(), usuarioCriado.getPassword());
        assertEquals(userDummy.getTelefone(), usuarioCriado.getTelefone());
        assertEquals(userDummy.getCep(), usuarioCriado.getCep());
        assertEquals(userDummy.getTipoUsuario(), usuarioCriado.getTipoUsuario());
    }

    @Test
    public void TestarEmailJaCadastrado() {
        CreateUsuarioDTO userDummy = new CreateUsuarioDTO();
        userDummy.setUsername("Maria");
        userDummy.setEmail("sr.jackdias@gmail.com");
        userDummy.setPassword("5432");
        userDummy.setTelefone("991834");
        userDummy.setCep("49328");
        userDummy.setTipoUsuario("Trabalhador");

        usuarioService.createUsuario(userDummy);

        EmailJaCadastradoException exception = assertThrows(EmailJaCadastradoException.class, () -> {
            usuarioService.createUsuario(userDummy);
        });
        assertEquals("O e-mail já está cadastrado: " + userDummy.getEmail(), exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "'', sr.jackdias@gmail.com, 5432, 991834, 49328, O campo nome é obrigatório.",
            "Maria, '', 5432, 991834, 49328, O campo email é obrigatório.",
            "Maria, sr.jackdias@gmail.com, '', 991834, 49328, O campo senha é obrigatório.",
            "Maria, sr.jackdias@gmail.com, 5432, '', 49328, O campo telefone é obrigatório.",
            "Maria, sr.jackdias@gmail.com, 5432, 991834, '', O campo CEP é obrigatório."
    })
    public void testarCampoObrigatorioVazio(String nome, String email, String senha, String telefone, String cep,
            String mensagemEsperada) {
        CreateUsuarioDTO userDummy = new CreateUsuarioDTO();
        userDummy.setUsername(nome);
        userDummy.setEmail(email);
        userDummy.setPassword(senha);
        userDummy.setTelefone(telefone);
        userDummy.setCep(cep);
        userDummy.setTipoUsuario("Trabalhador");

        CampoObrigatorioException exception = assertThrows(CampoObrigatorioException.class, () -> {
            usuarioService.createUsuario(userDummy);
        });

        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    public void testLoginComSucesso() {
        // Criação do usuário para o teste de login
        CreateUsuarioDTO userDummy = new CreateUsuarioDTO();
        userDummy.setUsername("teste");
        userDummy.setEmail("jr.jackdias@gmail.com");
        userDummy.setPassword("5432");
        userDummy.setTelefone("12345");
        userDummy.setCep("88049317");
        userDummy.setTipoUsuario("cliente");

        // Criando o usuário no banco de dados
        usuarioService.createUsuario(userDummy);

        // Teste de login com dados válidos
        String email = userDummy.getEmail();
        String password = userDummy.getPassword();

        // Chamando o método de login e verificando se o login é bem-sucedido
        boolean loginSucesso = usuarioService.loginUsuario(email, password);

        // Verifica se o login foi realizado com sucesso
        assertTrue(loginSucesso);
    }

    @Test
    public void testeUsuarioNaoExiste() {
        // Dados de um usuário inexistente
        String email = "usuarioinexistente@gmail.com";
        String password = "senhaerrada";

        // Chama o método de login e verifica o resultado
        usuarioService.loginUsuario(email, password);
        String mensagem = usuarioService.getMensagemLogin(); // Obtém a mensagem

        // Verifica se o login falhou e a mensagem de erro é a esperada
        assertEquals("Conta não existe.", mensagem);
    }

    @Test
    public void deveTestarSenhaIncorreta() {
        String email = "sr.jackdias@gmail.com"; // Email existente
        String password = "senhaerrada"; // Senha incorreta

        // Chama o método de login
        boolean loginSucesso = usuarioService.loginUsuario(email, password);

        // Verifica se o login falhou
        assertFalse(loginSucesso);

        // Verifica se a mensagem de erro retornada é a esperada
        String mensagem = usuarioService.getMensagemLogin();
        assertEquals("Conta não existe.", mensagem);
    }

    @Test
    public void deveAlterarOemailDoUsuario() {
        // Criação de um DTO para um novo usuário (para simular a criação do usuário)
        CreateUsuarioDTO userDummy = new CreateUsuarioDTO();
        userDummy.setUsername("teste");
        userDummy.setEmail("jr.jackdias@gmail.com");
        userDummy.setPassword("5432");
        userDummy.setTelefone("12345");
        userDummy.setCep("88049317");
        userDummy.setTipoUsuario("cliente");

        // Salva o usuário no banco de dados para garantir que o ID existe
        UsuarioEntity usuarioSalvo = usuarioService.createUsuario(userDummy);

        // ID do usuário salvo
        long id_usuario = usuarioSalvo.getId_Usuario(); // Usando o ID do usuário que acabou de ser criado

        // Criação de um DTO com o novo email
        ChangeUsuarioDTO dto = new ChangeUsuarioDTO();
        dto.setEmail("dudu@gmail.com");

        // Chama o serviço para alterar os dados do usuário
        usuarioService.changeUsuarioInfosById(id_usuario, dto);

        // Verifica se o email foi realmente alterado
        UsuarioEntity usuario = usuarioRepository.findById(id_usuario).orElse(null); // Busca pelo ID

        // Verifica que o usuário não é nulo
        assertNotNull(usuario, "Usuário não encontrado no banco de dados.");

        // Verifica se o email foi atualizado corretamente
        assertEquals("dudu@gmail.com", usuario.getEmail(), "E-mail alterado com sucesso");
    }

    @Test
    public void devecadastrarUmaNovaSenha() {

        CreateUsuarioDTO userDummy = new CreateUsuarioDTO();
        userDummy.setUsername("teste");
        userDummy.setEmail("jr.jackdias@gmail.com");
        userDummy.setPassword("5432");
        userDummy.setTelefone("12345");
        userDummy.setCep("88049317");
        userDummy.setTipoUsuario("cliente");

        // Salva o usuário no banco de dados para garantir que o ID existe
        UsuarioEntity usuarioSalvo = usuarioService.createUsuario(userDummy);

        // ID do usuário salvo
        long id_usuario = usuarioSalvo.getId_Usuario(); // Usando o ID do usuário que acabou de ser criado

        // Criação de um DTO com o novo email
        ChangeUsuarioDTO dto = new ChangeUsuarioDTO();
        dto.setPassword("5432");
        dto.setNewPassword("666999");

        // Chama o serviço para alterar os dados do usuário
        usuarioService.changeUsuarioInfosById(id_usuario, dto);

        // Verifica se o email foi realmente alterado
        UsuarioEntity usuario = usuarioRepository.findById(id_usuario).orElse(null); // Busca pelo ID

        // Verifica que o usuário não é nulo
        assertNotNull(usuario, "Usuário não encontrado no banco de dados.");

        // Verifica se o email foi atualizado corretamente
        assertEquals("666999", usuario.getPassword(), "Senha alterada com sucesso.");
    }

    @Test
    public void deveAlterarSenhaComSenhaAtualIncorreta() {
        // 1. Dado que o usuário está logado e acessa o perfil
        CreateUsuarioDTO userDummy = new CreateUsuarioDTO();
        userDummy.setUsername("teste");
        userDummy.setEmail("jr.jackdias@gmail.com");
        userDummy.setPassword("5432"); // Senha original
        userDummy.setTelefone("12345");
        userDummy.setCep("88049317");
        userDummy.setTipoUsuario("cliente");

        // Salva o usuário no banco de dados para garantir que o ID existe
        UsuarioEntity usuarioSalvo = usuarioService.createUsuario(userDummy);

        // 2. Quando tenta alterar a senha errando sua senha atual
        long id_usuario = usuarioSalvo.getId_Usuario(); // Usando o ID do usuário que acabou de ser criado

        // Criação de um DTO com a senha incorreta
        ChangeUsuarioDTO dto = new ChangeUsuarioDTO();
        dto.setPassword("666999"); // Nova senha que será tentada, mas sem a senha atual correta
        dto.setNewPassword("4322");

        // 3. Verifica se o sistema lança a exceção de "Senha atual incorreta"
        SenhaIncorretaException exception = assertThrows(SenhaIncorretaException.class, () -> {
            usuarioService.changeUsuarioInfosById(id_usuario, dto);
        });

        // 4. Então o sistema exibe "Senha atual incorreta"
        assertEquals("Senha atual incorreta", exception.getMessage());

        // 5. Verifica que a senha não foi alterada

    }

    @Test
    public void deveExibirOsTrabalhosPostados() {
        // Criar um cliente válido para o teste
        CreateUsuarioDTO userDummyCliente = new CreateUsuarioDTO();
        userDummyCliente.setUsername("jackson");
        userDummyCliente.setEmail("sr.jackdias@gmail.com");
        userDummyCliente.setPassword("1234");
        userDummyCliente.setTelefone("12345");
        userDummyCliente.setCep("88049317");
        userDummyCliente.setTipoUsuario("cliente");

        // Criar um trabalhador válido para o teste
        CreateUsuarioDTO userDummyTrabalhador = new CreateUsuarioDTO();
        userDummyTrabalhador.setUsername("pedro");
        userDummyTrabalhador.setEmail("pedro@gmail.com");
        userDummyTrabalhador.setPassword("1234");
        userDummyTrabalhador.setTelefone("54321");
        userDummyTrabalhador.setCep("88049318");
        userDummyTrabalhador.setTipoUsuario("trabalhador");

        // Salvar o cliente e o trabalhador no banco de dados
        UsuarioEntity cliente = usuarioService.createUsuario(userDummyCliente); // A criação do usuário retorna o objeto
                                                                                // salvo
        UsuarioEntity trabalhador = usuarioService.createUsuario(userDummyTrabalhador); // Criando o trabalhador
        // Verificar se o cliente e o trabalhador foram salvos com ID
        assertNotNull(cliente.getId_Usuario(), "O cliente deve ter um ID após ser salvo");
        assertNotNull(trabalhador.getId_Usuario(), "O trabalhador deve ter um ID após ser salvo");

        // Definir os dados do trabalho solicitado
        CreateTrabalhoSolicitadoDTO workUser = new CreateTrabalhoSolicitadoDTO();
        workUser.setTipo("Jardinagem");
        workUser.setValor(400);
        workUser.setLocalizacao("Campeche");
        workUser.setDescricao("Cortar grama");
        workUser.setStatus(true); // Definir o status aqui já
        workUser.setId_cliente(cliente.getId_Usuario()); // Usando o ID do cliente
        workUser.setId_trabalhador(trabalhador.getId_Usuario()); // Usando o ID do trabalhador

        // Criar o trabalho solicitado no banco de dados
        trabalhoSolicitadoService.createTrabalhoSolicitado(workUser, cliente);

        // Buscar todos os trabalhos postados
        List<ShowTrabalhoSolicitadoDTO> trabalhosDisponiveis = trabalhoSolicitadoService.getAllTrabalhoSolicitado();

        // Verificar se a lista de trabalhos não está vazia
        assertFalse(trabalhosDisponiveis.isEmpty(), "A lista de trabalhos não pode estar vazia");

        // Verificar os atributos do trabalho solicitado
        ShowTrabalhoSolicitadoDTO trabalho = trabalhosDisponiveis.get(0);
        assertEquals("Jardinagem", trabalho.getTipo());
        assertEquals(400, trabalho.getValor());
        assertEquals("Campeche", trabalho.getLocalizacao());
        assertTrue(trabalho.isStatus());
        assertEquals("Cortar grama", trabalho.getDescricao()); // Verificando a descrição
    }

    @Test
public void deveFiltrarEPesquisarTrabalhosPostados() {
    // Criação de um cliente fictício
    CreateUsuarioDTO clienteDTO = new CreateUsuarioDTO();
    clienteDTO.setUsername("Maria");
    clienteDTO.setEmail("maria@gmail.com");
    clienteDTO.setPassword("senha123");
    clienteDTO.setTelefone("123456789");
    clienteDTO.setCep("12345678");
    clienteDTO.setTipoUsuario("cliente");

    // Criando o cliente no sistema
    UsuarioEntity cliente = usuarioService.createUsuario(clienteDTO);

    // Criação de um trabalhador fictício
    CreateUsuarioDTO trabalhadorDTO = new CreateUsuarioDTO();
    trabalhadorDTO.setUsername("João");
    trabalhadorDTO.setEmail("joao@gmail.com");
    trabalhadorDTO.setPassword("senha123");
    trabalhadorDTO.setTelefone("987654321");
    trabalhadorDTO.setCep("87654321");
    trabalhadorDTO.setTipoUsuario("trabalhador");

    // Criando o trabalhador no sistema
    UsuarioEntity trabalhador = usuarioService.createUsuario(trabalhadorDTO);

    // Criação do primeiro trabalho solicitado
    CreateTrabalhoSolicitadoDTO trabalho1 = new CreateTrabalhoSolicitadoDTO();
    trabalho1.setTipo("Jardinagem");
    trabalho1.setValor(400);
    trabalho1.setLocalizacao("Centro");
    trabalho1.setDescricao("Cortar a grama e podar as plantas");
    trabalho1.setStatus(true);
    trabalho1.setId_cliente(cliente.getId_Usuario());
    trabalho1.setId_trabalhador(trabalhador.getId_Usuario());

    // Salvando o trabalho de jardinagem no sistema
    trabalhoSolicitadoService.createTrabalhoSolicitado(trabalho1, cliente);

    // Criação do segundo trabalho solicitado
    CreateTrabalhoSolicitadoDTO trabalho2 = new CreateTrabalhoSolicitadoDTO();
    trabalho2.setTipo("Limpeza");
    trabalho2.setValor(200);
    trabalho2.setLocalizacao("Campeche");
    trabalho2.setDescricao("Limpar o quintal e organizar os móveis");
    trabalho2.setStatus(true);
    trabalho2.setId_cliente(cliente.getId_Usuario());
    trabalho2.setId_trabalhador(trabalhador.getId_Usuario());

    // Salvando o trabalho de limpeza no sistema
    trabalhoSolicitadoService.createTrabalhoSolicitado(trabalho2, cliente);

    // Buscando todos os trabalhos solicitados no sistema
    List<ShowTrabalhoSolicitadoDTO> resultados = trabalhoSolicitadoService.getAllTrabalhoSolicitado();

    // Variáveis para verificar se os trabalhos foram encontrados nos resultados
    boolean trabalho1Encontrado = false;
    boolean trabalho2Encontrado = false;

    // Iterando sobre os trabalhos encontrados para validar os detalhes de cada um
    for (ShowTrabalhoSolicitadoDTO trabalho : resultados) {
        if (trabalho.getTipo().equals("Jardinagem")) {
            // Verifica se os detalhes do trabalho de jardinagem estão corretos
            assertEquals("Cortar a grama e podar as plantas", trabalho.getDescricao());
            assertEquals(400.0, trabalho.getValor(), 0.01);
            assertEquals("Centro", trabalho.getLocalizacao());
            trabalho1Encontrado = true;
        }

        if (trabalho.getTipo().equals("Limpeza")) {
            // Verifica se os detalhes do trabalho de limpeza estão corretos
            assertEquals("Limpar o quintal e organizar os móveis", trabalho.getDescricao());
            assertEquals(200.0, trabalho.getValor(), 0.01);
            assertEquals("Campeche", trabalho.getLocalizacao());
            trabalho2Encontrado = true;
        }
    }

    // Assegura que ambos os trabalhos foram encontrados nos resultados
    assertTrue(trabalho1Encontrado, "Trabalho de jardinagem deveria estar nos resultados.");
    assertTrue(trabalho2Encontrado, "Trabalho de limpeza deveria estar nos resultados.");
}


@Test
public void deveExibirTrabalhosPostadosPorUsuarioEspecifico() {
    // Dado que o usuário está autenticado na plataforma
    CreateUsuarioDTO clienteDTO = new CreateUsuarioDTO();
    clienteDTO.setUsername("Lucas");
    clienteDTO.setEmail("lucas@gmail.com");
    clienteDTO.setPassword("senha123");
    clienteDTO.setTelefone("123456789");
    clienteDTO.setCep("12345678");
    clienteDTO.setTipoUsuario("cliente");

    // Criar um cliente no banco de dados
    UsuarioEntity cliente = usuarioService.createUsuario(clienteDTO);

    CreateUsuarioDTO trabalhadorDTO = new CreateUsuarioDTO();
    trabalhadorDTO.setUsername("Carlos");
    trabalhadorDTO.setEmail("carlos@gmail.com");
    trabalhadorDTO.setPassword("senha123");
    trabalhadorDTO.setTelefone("987654321");
    trabalhadorDTO.setCep("87654321");
    trabalhadorDTO.setTipoUsuario("trabalhador");

    // Criar um trabalhador no banco de dados
    UsuarioEntity trabalhador = usuarioService.createUsuario(trabalhadorDTO);

    // Criar dois trabalhos para o trabalhador "Carlos"
    CreateTrabalhoSolicitadoDTO trabalho1 = new CreateTrabalhoSolicitadoDTO();
    trabalho1.setTipo("Limpeza");
    trabalho1.setValor(150);
    trabalho1.setLocalizacao("Centro");
    trabalho1.setDescricao("Limpar o apartamento");
    trabalho1.setStatus(true);
    trabalho1.setId_cliente(cliente.getId_Usuario());
    trabalho1.setId_trabalhador(trabalhador.getId_Usuario());

    trabalhoSolicitadoService.createTrabalhoSolicitado(trabalho1, cliente);

    CreateTrabalhoSolicitadoDTO trabalho2 = new CreateTrabalhoSolicitadoDTO();
    trabalho2.setTipo("Jardinagem");
    trabalho2.setValor(200);
    trabalho2.setLocalizacao("Cohab");
    trabalho2.setDescricao("Cortar grama e podar plantas");
    trabalho2.setStatus(true);
    trabalho2.setId_cliente(cliente.getId_Usuario());
    trabalho2.setId_trabalhador(trabalhador.getId_Usuario());

    trabalhoSolicitadoService.createTrabalhoSolicitado(trabalho2, cliente);

    // Quando o usuário navega até o perfil do trabalhador "Carlos"
    // E o usuário seleciona a opção para visualizar os trabalhos postados por "Carlos"
    List<ShowTrabalhoSolicitadoDTO> trabalhosDeCarlos = trabalhoSolicitadoService.getAllTrabalhoSolicitado();

    // Então o sistema deve exibir uma lista com os trabalhos postados por Carlos
    assertNotNull(trabalhosDeCarlos, "A lista de trabalhos não pode ser nula");

    // Verificar se a lista contém exatamente os dois trabalhos esperados
    boolean trabalho1Encontrado = false;
    boolean trabalho2Encontrado = false;

    // Iterar sobre os trabalhos para verificar os detalhes
    for (ShowTrabalhoSolicitadoDTO trabalho : trabalhosDeCarlos) {
        if (trabalho.getTipo().equals("Limpeza")) {
            assertEquals("Limpar o apartamento", trabalho.getDescricao(), "Descrição do trabalho Limpeza");
            assertEquals(150, trabalho.getValor(), 0.01, "Valor do trabalho Limpeza");
            assertEquals("Centro", trabalho.getLocalizacao(), "Localização do trabalho Limpeza");
            trabalho1Encontrado = true;
        }

        if (trabalho.getTipo().equals("Jardinagem")) {
            assertEquals("Cortar grama e podar plantas", trabalho.getDescricao(), "Descrição do trabalho Jardinagem");
            assertEquals(200, trabalho.getValor(), 0.01, "Valor do trabalho Jardinagem");
            assertEquals("Cohab", trabalho.getLocalizacao(), "Localização do trabalho Jardinagem");
            trabalho2Encontrado = true;
        }
    }

    // Assegurar que ambos os trabalhos foram encontrados
    assertTrue(trabalho1Encontrado, "O trabalho de Limpeza deveria estar na lista de trabalhos.");
    assertTrue(trabalho2Encontrado, "O trabalho de Jardinagem deveria estar na lista de trabalhos.");
}

    

}