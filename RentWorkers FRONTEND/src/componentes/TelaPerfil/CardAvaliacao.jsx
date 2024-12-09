import React, { useState } from "react";
import { dadosUsuarioLogado, deletarAvaliacao } from "../../config/axios";
import './CardAvaliacao.css'
import { CiTrash } from "react-icons/ci";


function CardAvaliacao({ avaliacao }) {
    const { id_avaliacao, id_usuario, nota_avaliacao, texto_avaliativo, id_trabalhador } = avaliacao;
    const [nomeCliente, setNomeCliente] = useState("");

    dadosUsuarioLogado(id_usuario).then((response) => {
        const usuario = response.data
        setNomeCliente(usuario.username)
        
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

    return (
        <div className="card-avaliacao">
            <p><strong>Enviado pelo cliente:</strong> {nomeCliente}</p>
            <p><strong>Nota:</strong> {renderEstrelas(nota_avaliacao)}</p>
            <p><strong>Avaliação:</strong> {texto_avaliativo}</p>

        </div>
    );
}

export default CardAvaliacao;
