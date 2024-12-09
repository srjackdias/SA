import React, { useState } from 'react';
import '../TelaDeBusca/NavBusca.css';
import DadosPessoais from './DadosPessoais';
import IconBusca from './IconBusca';

function NavBusca() {
  const [inputNome, setInputNome] = useState('');
  
  const [dados] = useState([
    { id: 1, nome: "Marcos", funcao: "Faxineiro", localizacao: "Tapera", imagemPerfil: "/images/download 30.png" },
    { id: 2, nome: "Loan", funcao: "Porteiro", localizacao: "Balneário", imagemPerfil: "/images/download 8@2x.png" },
    { id: 3, nome: "Arthur", funcao: "Pedreiro", localizacao: "Ribeirao", imagemPerfil: "/images/download 30.png" },
    { id: 4, nome: "Flash", funcao: "Estudante", localizacao: "Manaus", imagemPerfil: "/images/download 8@2x.png" },
    { id: 5, nome: "Isabela", funcao: "Recepcionista", localizacao: "Algum lugar", imagemPerfil: "/images/download 30.png" },
    { id: 6, nome: "Rosa", funcao: "Faxineira", localizacao: "Saco grande", imagemPerfil: "/images/download 8@2x.png" },
    { id: 7, nome: "Marcio", funcao: "Copeiro", localizacao: "Manaus", imagemPerfil: "/images/download 30.png" },
    { id: 8, nome: "Marcia", funcao: "Domestica", localizacao: "Manaus", imagemPerfil: "/images/download 8@2x.png" },
    { id: 9, nome: "Rui Costa", funcao: "Programador", localizacao: "Florianopolis", imagemPerfil: "/images/download 30.png" },
    { id: 10, nome: "Raisa Nascimento", funcao: "Psicologa", localizacao: "Manaus", imagemPerfil: "/images/download 8@2x.png"},

  ]);

  const [dadosFiltrados, setDadosFiltrados] = useState(dados);

  const BuscarTrabalhador = () => {
    if (!inputNome.trim()) {
      setDadosFiltrados(dados);
      return;
    }
    const filtrados = dados.filter(d => d.nome.toLowerCase().includes(inputNome.toLowerCase()));
    setDadosFiltrados(filtrados);
  };

  const handleKeyDown = (event) => {
    if (event.key === 'Enter') {
      BuscarTrabalhador();
    }
  };

  return (
    <>
      <div className='NavContainer'>
        <img className='imgLogo' src='/images/LogoRentWorkers.png' alt="Logo" />

        <div className='divSelectPainel'>
          <h1 className='TituloUm'>Inicio</h1>
        </div>

        <div className='divSelectPainelMeio'>
          <label className='ordernarpow'>ORDENAR POR:</label>
          <select className='selectFiltro'>
            <option value="">Nenhum</option>
            <option value=""></option>
          </select>
        </div>

        <div className='divSelectPainelDois'>
          <input
            className='inputStyle'
            placeholder='Buscar'
            value={inputNome}
            onChange={(event) => setInputNome(event.target.value)}
            onKeyDown={handleKeyDown}
          />
          <button onClick={BuscarTrabalhador} type="button" className="btnBuscar">
            <IconBusca style={{ width: '20px', height: '20px' }} />
          </button>
          <img className='imgPerfil' src='/images/download 46 (1).png' alt="Perfil" />
        </div>
      </div>

      <div className='cards'>
        {dadosFiltrados.map((d) => (
          <DadosPessoais dado={d} key={d.id} />
        ))}
      </div>
    </>
  );
}

export default NavBusca;
