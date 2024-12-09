import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../TelaPrincipal/DadosPessoais.css';

function CardTrabalhador({ username, localizacao, especializacao, onClick }) {
  const [cidadeEstado, setCidadeEstado] = useState('');

  const obterCidadeEstado = async (cep) => {
    if (!cep) {
      setCidadeEstado('CEP não informado');
      return;
    }

    const cepFormatado = cep.replace(/[^\d]+/g, ''); 

    if (cepFormatado.length !== 8) {
      setCidadeEstado('CEP inválido');
      return;
    }

    try {
      const response = await axios.get(`https://viacep.com.br/ws/${cepFormatado}/json/`);

      if (response.data.localidade && response.data.uf) {
        setCidadeEstado(`${response.data.localidade} / ${response.data.uf}`);
      } else {
        setCidadeEstado('Localização não encontrada');
      }
    } catch (error) {
      console.error('Erro ao buscar a localização:', error);
      setCidadeEstado('Erro ao buscar localização');
    }
  };

  useEffect(() => {
    if (localizacao) {
      obterCidadeEstado(localizacao);  
    } else {
      setCidadeEstado('CEP não informado');
    }
  }, [localizacao]);  

  return (
    <div className="card-container">
      <img className="imgPerfilUm" src="images/imagem-perfil-png.png" alt={`Foto de ${username}`} />
      <p>Nome: {username}</p>
      <p>Localização: {cidadeEstado}</p>
      <p>Especialização: {especializacao}</p>
      <button onClick={onClick}>Solicitar Serviço</button>
    </div>
  );
}

export default CardTrabalhador;
