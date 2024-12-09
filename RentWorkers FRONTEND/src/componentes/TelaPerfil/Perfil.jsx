import React, { useState, useEffect, useContext } from "react";
import "./Perfil.css";
import { avalicoesVinculadosAoTrabalhadorLogado, listaUsuarios } from "../../config/axios";
import { atualizarUsuario, dadosUsuarioLogado, excluirUsuario, trabalhosVinculadosAoTrabalhadorLogado } from "../../config/axios";
import { UserContext } from "../../context/GlobalContext";
import CardTrabalho from "./CardTrabalho";
import CardAvaliacao from "./CardAvaliacao";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Perfil() {

    const navigate = useNavigate();

    const { usuario, logout } = useContext(UserContext);

    const [estadoAnteriorUsername, setEstadoInteriorUsername] = useState();
    const [estadoAnteriorEmail, setEstadoAnteriorEmail] = useState();
    const [estadoAnteriorTelefone, setEstadoAnteriorTelefone] = useState();
    const [estadoAnteriorCep, setEstadoAnteriorCep] = useState();

    const [email, setEmail] = useState("");
    const [telefone, setTelefone] = useState("");
    const [cep, setCep] = useState("");
    const [username, setUsername] = useState('');
    const [editarUsuario, setEditarUsuario] = useState(false);
    const [telaDados, setTelaDados] = useState(true)
    const [mostrarTrabalhos, setMostrarTrabalhos] = useState(false);
    const [mostrarAvaliacoes, setMostrarAvaliacoes] = useState(false);
    const [avaliacoes, setAvaliacoes] = useState([])
    const [trabalhos, setTrabalhos] = useState([]);
    const [usuarios, setUsuarios] = useState([]);

    useEffect(() => {
        if (usuario) {
            dadosUsuarioLogado(usuario.id_usuario)
                .then((response) => {
                    setEmail(usuario.email);
                    setTelefone(usuario.telefone);
                    setCep(usuario.cep);
                    setUsername(usuario.username);

                    localStorage.setItem('usuario', JSON.stringify(response.data))

                    console.log(response.data);
                })
                .catch((error) => {
                    console.log("Erro ao carregar dados do usuário:", error);
                });

            trabalhosVinculadosAoTrabalhadorLogado(usuario.id_usuario).then((response) => {
                setTrabalhos(response.data);
            })

            avalicoesVinculadosAoTrabalhadorLogado(usuario.id_usuario).then((response) => {
                setAvaliacoes(response.data)
            })

        } else {
            console.log("Não está logado");
        }
    }, [usuario]);



    function atualizarEdicao() {
        const dadosAtualizado = {
            id_usuario: usuario.id_usuario,
            email: email,
            telefone: telefone,
            cep: cep,
            username: username,
        };

        let emailExiste = false
        let telefoneExiste = false


        listaUsuarios().then((response) => {
            setUsuarios(response.data);
        }).catch(error => {
            console.log(error);
        })

        for (let i = 0; i < usuarios.length; i++) {
            if (usuarios[i].id_usuario != usuario.id_usuario) {
                if (usuarios[i].email == email) {
                    emailExiste = true
                }
                if (usuarios[i].telefone == telefone) {
                    telefoneExiste = true
                }
            }
        }

        if (emailExiste) {
            alert("Este email ja existe!")
        }
        if (telefoneExiste) {
            alert("Este telefone ja existe!")
        }

        if (!emailExiste && !telefoneExiste) {
            atualizarUsuario(dadosAtualizado)
                .then(() => {
                    alert("Informações atualizadas com sucesso");
                    mostrarDados();
                })
                .catch((error) => {
                    console.error("Erro ao atualizar os dados do usuário", error);
                    alert("Erro ao salvar informações. Tente novamente.");
                });
        }

    }

    function mostrarSolicitacoes() {
        setMostrarTrabalhos(true);
        setEditarUsuario(false);
        setTelaDados(false);
        setMostrarAvaliacoes(false)
    }

    function mostrarDados() {
        setTelaDados(true);
        setMostrarTrabalhos(false);
        setEditarUsuario(false);
        setMostrarAvaliacoes(false)
    }

    function mostrarEdicao() {
        setEditarUsuario(true);
        setMostrarTrabalhos(false);
        setMostrarAvaliacoes(false)
        setTelaDados(false);
        setEstadoInteriorUsername(username);
        setEstadoAnteriorEmail(email);
        setEstadoAnteriorTelefone(telefone);
        setEstadoAnteriorCep(cep);
    }

    function fecharEdicaoPerfil() {
        setEditarUsuario(false);
        setMostrarTrabalhos(false);
        setMostrarAvaliacoes(false)
        setTelaDados(true);
        setUsername(estadoAnteriorUsername);
        setEmail(estadoAnteriorEmail);
        setTelefone(estadoAnteriorTelefone);
        setCep(estadoAnteriorCep);
    }

    function telaAvaliacoes() {
        setMostrarAvaliacoes(true)
        setMostrarTrabalhos(false);
        setEditarUsuario(false);
        setTelaDados(false);
    }

    function voltarTelaPrincipal() {
        setTelaDados(false)
        setMostrarTrabalhos(false)
        setEditarUsuario(false)
        navigate('/telaprincipal')
    }

    function excluirConta() {
        excluirUsuario(usuario.id_usuario).then((response) => {
            console.log(response.data)
            localStorage.clear();
            navigate('/')
        }).catch((error) => {
            console.log(error)
        })
    }

    function sairDaConta() {
        logout();
    }

    return (
        <div className="container-Perfil">
            <div className="divPerfil">
                <div className="div-cima">
                    <img className="imgPerfilum" src="/images/download 46 (1).png" alt="Perfil" />
                    <p className="fonteNome">PAINEL DO TRABALHADOR</p>
                </div>

                <div className="divPerfil-Esquerda">
                    <div className="div-dados" onClick={mostrarDados}><p>Dados</p></div>
                    <div className="div-solicitacoes" onClick={mostrarSolicitacoes}>
                        <p>Solicitações de trabalho</p>
                    </div>
                    <div onClick={telaAvaliacoes}><p>Avaliações</p></div>
                    <div className="div-ExcluirConta" onClick={excluirConta}>Excluir Conta</div>
                    <div className="div-Sair" onClick={sairDaConta}>Sair</div>
                </div>

                <div className="div-meio">
                    {mostrarTrabalhos && (
                        <div className="listaTrabalhos">
                            {trabalhos.length === 0 ? (
                                <p>Nenhuma solicitação de trabalho encontrada.</p>
                            ) : (
                                trabalhos.map((trabalho, index) => (
                                    <CardTrabalho key={index} trabalho={trabalho} usuario={usuario} />
                                ))
                            )}
                        </div>
                    )}
                    {telaDados && (
                        <div className="dados-usuario">
                            <p><strong>Username:</strong> {username}</p>
                            <p><strong>Email:</strong> {email}</p>
                            <p><strong>Telefone:</strong> {telefone}</p>
                            <p><strong>CEP:</strong> {cep}</p>

                            <div className="div-botoes">
                                <button onClick={mostrarEdicao} className="botaoInfo">Editar Informações</button>
                            </div>
                        </div>
                    )}

                    {editarUsuario && (
                        <div className="formulario-edicao">
                            <div className="campo-edicao">
                                <label htmlFor="username">Username:</label>
                                <input
                                    id="username"
                                    type="text"
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                    placeholder="Digite seu username"
                                />
                            </div>

                            <div className="campo-edicao">
                                <label htmlFor="email">Email:</label>
                                <input
                                    id="email"
                                    type="email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    placeholder="Digite seu email"
                                />
                            </div>

                            <div className="campo-edicao">
                                <label htmlFor="telefone">Telefone:</label>
                                <input
                                    id="telefone"
                                    type="tel"
                                    value={telefone}
                                    onChange={(e) => setTelefone(e.target.value)}
                                    placeholder="Digite seu telefone"
                                />
                            </div>

                            <div className="campo-edicao">
                                <label htmlFor="cep">CEP:</label>
                                <input
                                    id="cep"
                                    type="text"
                                    value={cep}
                                    onChange={(e) => setCep(e.target.value)}
                                    placeholder="Digite seu CEP"
                                />
                            </div>

                            <div className="div-botoes">
                                <button onClick={atualizarEdicao} className="botaoSalvar">Salvar</button>
                                <button onClick={fecharEdicaoPerfil} className="botaoInfo">Cancelar</button>
                            </div>
                        </div>


                    )}
                    {mostrarAvaliacoes && (
                        <div className="perfil-lista-avaliacoes">
                            {avaliacoes.length === 0 ? (
                                <p>Nenhuma avaliação encontrada.</p>
                            ) : (
                                avaliacoes.map((avaliacao, index) => (
                                    <CardAvaliacao key={index} avaliacao={avaliacao} />
                                ))
                            )}
                        </div>
                    )}
                    <div>

                    </div>
                </div>
            </div>
        </div>
    )
}

export default Perfil;
