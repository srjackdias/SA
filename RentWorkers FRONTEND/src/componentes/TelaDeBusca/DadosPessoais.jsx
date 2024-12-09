import React from 'react';
import './DadosPessoais.css';

function DadosPessoais(props) {
  let d = props.dado; 

  return (
    <div className="dadosPessoais-container">
       <img className='imgPerfilUm' src={d.imagemPerfil} alt={`Foto de ${d.nome}`} /> 

      <p>Nome: {d.nome}</p>
      <p>Função: {d.funcao}</p> 
      <p>Localização: {d.localizacao}</p>
      <p>id: {d.id}</p> 
    </div>
  );
}

export default DadosPessoais;
