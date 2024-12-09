import React, { useState } from "react";
import Modal from "react-modal"; // Importando o react-modal
import axios from "axios";
import { dadosUsuarioLogado } from "../../config/axios";
import "./CardTrabalhoCliente.css";

// Estilização para o Modal
Modal.setAppElement("#root");

const CardTrabalhoCliente = ({ trabalho, usuario }) => {
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [avaliacao, setAvaliacao] = useState(0);
    const [comentario, setComentario] = useState("");
    const [nomeTrabalhador, setNomeTrabalhador] = useState("");


    const statusTrabalho = trabalho.status
        ? "Solicitação de trabalho aceita."
        : "Aguardando a aceitação do trabalho";

    const handleStatusChange = (id_trabalho_solicitado, status) => {
        const newStatus = status ? false : true;
        axios
            .put(`http://127.0.0.1:8080/trabalhos/change-status/${id_trabalho_solicitado}`, { status: newStatus })
            .then(() => {
                alert(`Status do trabalho alterado para: ${newStatus ? "Aceito" : "Negado"}`);
            })
            .catch((error) => {
                console.error("Erro ao atualizar status", error);
                alert("Erro ao atualizar o status do trabalho.");
            });
    };

    dadosUsuarioLogado(trabalho.id_trabalhador).then((response) => {
        const usuario = response.data
        setNomeTrabalhador(usuario.username)
        
    }).catch((error) => {
        console.log(error);
    })

    const enviarAvaliacao = () => {
        console.log(trabalho)
        let avalicaoCompleta = true;
        let comentarioPreenchido = true;

        if (avaliacao < 1) {
            avalicaoCompleta = false;
        }
        if (comentario == "") {
            comentarioPreenchido = false;
        }

        if (avaliacao && comentarioPreenchido) {
            let dadosAvaliacao = {
                id_usuario: usuario.id_usuario,
                nota_avaliacao: avaliacao,
                texto_avaliativo: comentario,
                id_trabalhador: trabalho.id_trabalhador
            }

            axios.post(`http://127.0.0.1:8080/avaliacoes`, dadosAvaliacao)
                .then(() => {
                    alert("Avaliação enviada com sucesso!");
                    setModalIsOpen(false); // Fechar o modal após o envio
                })
                .catch((error) => {
                    console.error("Erro ao enviar avaliação", error);
                    alert("Erro ao enviar a avaliação.");
                });
        }else {
            alert("Preencha todos os campos");
        }

    };

    return (
        <div className="cardTrabalho">
            <div className="cardHeader">
                <h3>{trabalho.tipo}</h3>
            </div>
            <div className="cardBody">
                <p><strong>Nome do trabalhador: </strong> {nomeTrabalhador}</p>
                <p><strong>Valor:</strong> R${trabalho.valor}</p>
                <p><strong>Localização:</strong> {trabalho.localizacao}</p>
                <p><strong>Descrição:</strong> {trabalho.descricao}</p>
                <p><strong>Status:</strong> {statusTrabalho}</p>
                {usuario.tipoUsuario === "TRABALHADOR" && !trabalho.status && (
                    <div className="botoes-trabalho">
                        <button onClick={() => handleStatusChange(trabalho.id_trabalho_solicitado, trabalho.status)} className="botaoAceitar">Aceitar</button>
                        <button onClick={() => handleStatusChange(trabalho.id_trabalho_solicitado, trabalho.status)} className="botaoNegar">Negar</button>
                    </div>
                )}
                {trabalho.status && (
                    <button onClick={() => setModalIsOpen(true)} className="botaoAvaliar">Avaliar</button>
                )}
            </div>

            {/* Modal para avaliação */}
            <Modal
                isOpen={modalIsOpen}
                onRequestClose={() => setModalIsOpen(false)}
                className="modal"
                overlayClassName="modalOverlay"
            >
                <h2>Avaliar Trabalhador</h2>
                <div className="modalContent">
                    <label>
                        <strong>Avaliação (1 a 5 estrelas):</strong>
                    </label>
                    <div className="estrelas">
                        {[1, 2, 3, 4, 5].map((estrela) => (
                            <span
                                key={estrela}
                                className={`estrela ${avaliacao >= estrela ? "ativa" : ""}`}
                                onClick={() => setAvaliacao(estrela)}
                                style={{ cursor: "pointer", fontSize: "24px" }}
                            >
                                ★
                            </span>
                        ))}
                    </div>
                    <label>
                        <strong>Comentário:</strong>
                    </label>
                    <textarea
                        value={comentario}
                        onChange={(e) => setComentario(e.target.value)}
                        placeholder="Escreva sua avaliação..."
                    ></textarea>
                    <div className="modalButtons">
                        <button onClick={enviarAvaliacao} className="botaoEnviar">Enviar Avaliação</button>
                        <button onClick={() => setModalIsOpen(false)} className="botaoFechar">Fechar</button>
                    </div>
                </div>
            </Modal>
        </div>
    );
};

export default CardTrabalhoCliente;
