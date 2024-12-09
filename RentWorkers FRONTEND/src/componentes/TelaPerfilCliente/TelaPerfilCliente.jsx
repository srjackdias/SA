import React, { useState, useEffect, useContext } from "react";
import "./TelaPerfilCliente.css";
import { listaUsuarios } from "../../config/axios";
import { UserContext } from "../../context/GlobalContext";
import { atualizarUsuario, dadosUsuarioLogado, excluirUsuario } from "../../config/axios";
import { Hamburger, Plus, InformationSquare, OpenPadlock, Trash, Settings } from "./IconPerfil";
import CardTrabalhoCliente from "./CardTrabalhoCliente";

import { useNavigate } from "react-router-dom";
import CardAvaliacaoCliente from "./CardAvaliacaoCliente";

function TelaPerfilCliente() {
    const navigate = useNavigate();

    const { usuario } = useContext(UserContext);

    const [estadoAnteriorUsername, setEstadoInteriorUsername] = useState();
    const [estadoAnteriorEmail, setEstadoAnteriorEmail] = useState();
    const [estadoAnteriorTelefone, setEstadoAnteriorTelefone] = useState();
    const [estadoAnteriorCep, setEstadoAnteriorCep] = useState();

    const [email, setEmail] = useState("");
    const [telefone, setTelefone] = useState("");
    const [cep, setCep] = useState("");
    const [username, setUsername] = useState('');
    const [editarUsuario, setEditarUsuario] = useState(false);
    const [telaDados, setTelaDados] = useState(true);
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

                    localStorage.setItem('usuario', JSON.stringify(response.data));

                    if (response.data.trabalhos) {
                        setTrabalhos(response.data.trabalhos);
                    }
                    if (response.data.avaliacoes) {
                        setAvaliacoes(response.data.avaliacoes)
                    }

                    console.log(response.data);
                })
                .catch((error) => {
                    console.log("Erro ao carregar dados do usuário:", error);
                });
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

        for(let i = 0; i < usuarios.length; i++) {
            if(usuarios[i].id_usuario != usuario.id_usuario){
                if(usuarios[i].email == email) {
                   emailExiste = true
                }
                if(usuarios[i].telefone == telefone) {
                    telefoneExiste = true
                }
            }
        }

        if(emailExiste) {
            alert("Este email ja existe!")
        }
        if(telefoneExiste) {
            alert("Este telefone ja existe!")
        }
        
        if(!emailExiste && !telefoneExiste) {
            atualizarUsuario(dadosAtualizado)
            .then(() => {
                alert("Informações atualizadas com sucesso");
                window.location.reload()
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
        setTelaDados(false);
        setMostrarTrabalhos(false);
        setEditarUsuario(false);
        setMostrarAvaliacoes(false)
        navigate('/telaprincipal');
    }

    function excluirConta() {
        excluirUsuario(usuario.id_usuario).then((response) => {
            console.log(response);
            window.location.reload();
            localStorage.clear();
            navigate('/')
        }).catch((error) => {
            console.log(error);
        });
    }

    return (
        <div className="perfil-container">
            <div className="perfil-div">
                <div className="perfil-topo">
                    <img className="perfil-img" src="/images/download 46 (1).png" alt="Perfil" />
                    <p className="perfil-nome">{usuario ? `${usuario.username}` : "Nome do Usuário"}</p>
                </div>

                <div className="perfil-esquerda">
                    <div className="perfil-dados" onClick={mostrarDados}><p>Dados</p></div>
                    <div className="perfil-nota" onClick={telaAvaliacoes}><p>Nota e Avaliações</p></div>
                    <div className="perfil-solicitacoes" onClick={mostrarSolicitacoes}><p>Solicitações de trabalho</p></div>
                    <div className="perfil-excluir" onClick={excluirConta}>Excluir Conta</div>
                    <div className="perfil-voltar" onClick={voltarTelaPrincipal}>Voltar</div>
                </div>

                <div className="perfil-meio">
                    {mostrarTrabalhos ? (
                        <div className="perfil-lista">
                            {trabalhos.length === 0 ? (
                                <p>Nenhuma solicitação de trabalho encontrada.</p>
                            ) : (
                                trabalhos.map((trabalho, index) => (
                                    <CardTrabalhoCliente key={index} trabalho={trabalho} usuario={usuario} />
                                ))
                            )}
                        </div>
                    ) : (
                        <div>
                            {editarUsuario && (
                                <div className="perfil-formulario">
                                    <div className="perfil-campo">
                                        <label htmlFor="username">Username:</label>
                                        <input
                                            id="username"
                                            type="text"
                                            value={username}
                                            onChange={(e) => setUsername(e.target.value)}
                                            placeholder="Digite seu username"
                                        />
                                    </div>

                                    <div className="perfil-campo">
                                        <label htmlFor="email">Email:</label>
                                        <input
                                            id="email"
                                            type="email"
                                            value={email}
                                            onChange={(e) => setEmail(e.target.value)}
                                            placeholder="Digite seu email"
                                        />
                                    </div>

                                    <div className="perfil-campo">
                                        <label htmlFor="telefone">Telefone:</label>
                                        <input
                                            id="telefone"
                                            type="tel"
                                            value={telefone}
                                            onChange={(e) => setTelefone(e.target.value)}
                                            placeholder="Digite seu telefone"
                                        />
                                    </div>

                                    <div className="perfil-campo">
                                        <label htmlFor="cep">CEP:</label>
                                        <input
                                            id="cep"
                                            type="text"
                                            value={cep}
                                            onChange={(e) => setCep(e.target.value)}
                                            placeholder="Digite seu CEP"
                                        />
                                    </div>

                                    <div className="perfil-botoes">
                                        <button onClick={atualizarEdicao} className="perfil-salvar">Salvar</button>
                                        <button onClick={fecharEdicaoPerfil} className="perfil-cancelar">Cancelar</button>
                                    </div>
                                </div>
                            )}
                            {telaDados && (
                                <div className="perfil-dados-usuario">
                                    <p><strong>Username:</strong> {username}</p>
                                    <p><strong>Email:</strong> {email}</p>
                                    <p><strong>Telefone:</strong> {telefone}</p>
                                    <p><strong>CEP:</strong> {cep}</p>

                                    <div className="perfil-botoes">
                                        <button onClick={mostrarEdicao} className="perfil-editar">Editar Informações</button>
                                    </div>
                                </div>
                            )}
                            {mostrarAvaliacoes && (
                                <div className="perfil-lista-avaliacoes">
                                    {avaliacoes.length === 0 ? (
                                        <p>Nenhuma avaliação encontrada.</p>
                                    ) : (
                                        avaliacoes.map((avaliacao, index) => (
                                            <CardAvaliacaoCliente key={index} avaliacao={avaliacao} />
                                        ))
                                    )}
                                </div>
                            )}
                        </div>
                    )}
                </div>
            </div>
        </div>

    );
}

export default TelaPerfilCliente;
