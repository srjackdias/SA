import React, { useState } from "react";
import "./CardAvaliacaoCliente.css";
import { dadosUsuarioLogado, deletarAvaliacao } from "../../config/axios";
import { CiTrash } from "react-icons/ci";


function CardAvaliacaoCliente({ avaliacao }) {
    const { id_avaliacao, id_usuario, nota_avaliacao, texto_avaliativo, id_trabalhador } = avaliacao;
    const [nomeTrabalhador, setNomeTrabalhador] = useState("");

    dadosUsuarioLogado(id_trabalhador).then((response) => {
        const usuario = response.data
        setNomeTrabalhador(usuario.username)
        
    }).catch((error) => {
        console.log(error);
    })
    // Gera um array para representar as estrelas
    const renderEstrelas = (nota) => {
        const estrelas = [];
        for (let i = 1; i <= 5; i++) {
            estrelas.push(
                <span key={i} className={`estrela ${i <= nota ? "ativa" : ""}`}>
                    ★
                </span>
            );
        }
        return estrelas;
    };

    function deleteAvaliacao() {
        deletarAvaliacao(id_avaliacao).then((response) => {
           console.log(response);
           window.location.reload()
        }).catch((error) => {
            console.log(error);
        })
    }



    return (
        <div className="card-avaliacao">
            <div className="delete-avaliacao-button">
              <CiTrash onClick={deleteAvaliacao}/>
            </div>
            <p><strong>Enviado ao trabalhador:</strong> {nomeTrabalhador}</p>
            <p><strong>Nota:</strong> {renderEstrelas(nota_avaliacao)}</p>
            <p><strong>Avaliação:</strong> {texto_avaliativo}</p>

        </div>
    );
}

export default CardAvaliacaoCliente;
