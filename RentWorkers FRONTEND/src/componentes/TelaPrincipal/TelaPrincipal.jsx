import { useEffect, useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import '../TelaPrincipal/TelaPrincipal.css';
import { listaTrabalhadores } from '../../config/axios';
import { UserContext } from '../../context/GlobalContext';
import CardTrabalhador from './CardTrabalhador';
import Modal from 'react-modal';
import axios from 'axios';

Modal.setAppElement('#root');

function TelaPrincipal() {
  const navigate = useNavigate();
  const { logout, usuario } = useContext(UserContext);
  const [trabalhadores, setTrabalhadores] = useState([]);
  const [inptSearch, setInptSearch] = useState("");
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [trabalhadorSelecionado, setTrabalhadorSelecionado] = useState(null);
  const [descricaoProblema, setDescricaoProblema] = useState('');
  const [valorOferecido, setValorOferecido] = useState('');
  const [endereco, setEndereco] = useState('');
  const [id_trabalhador, setId_trabalhador] = useState('');
  const [estado, setEstado] = useState('');
  const [cidade, setCidade] = useState('');
  const [rua, setRua] = useState('');
  const [bairro, setBairro] = useState('');
  const [numeroCasa, setNumeroCasa] = useState('');
  const [localizacao, setLocalizacao] = useState('');
  const [trabalhadoresPesquisados, setTrabalhadoresPesquisados] = useState([]);

  useEffect(() => {
    listaTrabalhadores()
      .then((response) => {
        setTrabalhadores(response.data);
        setTrabalhadoresPesquisados(response.data); // Inicialmente, exibe todos os trabalhadores
      })
      .catch((error) => console.log(error));
  }, []);

  const handleSearchChange = (e) => {
    const query = e.target.value.toLowerCase();
    setInptSearch(query);

    // Filtrar trabalhadores em tempo real
    const filtrados = trabalhadores.filter((trabalhador) =>
      trabalhador.username.toLowerCase().includes(query) ||
      trabalhador.especialidade.toLowerCase().includes(query)
    );

    setTrabalhadoresPesquisados(filtrados);
  };

  const abrirModal = (trabalhador, usuario) => {
    setTrabalhadorSelecionado(trabalhador);
    setId_trabalhador(trabalhador.id_usuario);
    setModalIsOpen(true);
    setLocalizacao(trabalhador.cep);
    obterCidadeEstadoCliente(usuario.cep);
    obterCidadeEstadoTrabalhador(trabalhador.cep);
  };

  const fecharModal = () => {
    setModalIsOpen(false);
    setDescricaoProblema('');
    setValorOferecido('');
    setRua('');
    setBairro('');
    setCidade('');
    setEstado('');
    setNumeroCasa('');
  };

  const obterCidadeEstadoCliente = async (cep) => {
    try {
      const response = await axios.get(`https://viacep.com.br/ws/${cep}/json/`);
      if (response.data.erro) {
        setLocalizacao('Localização não encontrada');
      } else {
        setRua(response.data.logradouro);
        setBairro(response.data.bairro);
        setEstado(response.data.uf);
        setCidade(response.data.localidade);
      }
    } catch (error) {
      console.error('Erro ao buscar a localização:', error);
      setLocalizacao('Erro ao buscar localização');
    }
  };

  const obterCidadeEstadoTrabalhador = async (cep) => {
    try {
      const response = await axios.get(`https://viacep.com.br/ws/${cep}/json/`);
      if (response.data.erro) {
        setLocalizacao('Localização não encontrada');
      } else {
        setLocalizacao(`${response.data.localidade} / ${response.data.uf}`);
      }
    } catch (error) {
      console.error('Erro ao buscar a localização:', error);
      setLocalizacao('Erro ao buscar localização');
    }
  };

  const enviarSolicitacao = async () => {
    const idUsuarioLogado = usuario.id_usuario;
    if (!idUsuarioLogado) {
      console.error('ID do usuário não encontrado');
      return;
    }
    const dadosSolicitacao = {
      id_cliente: idUsuarioLogado,
      id_trabalhador: id_trabalhador,
      tipo: trabalhadorSelecionado.especialidade,
      descricao: descricaoProblema,
      valor: parseFloat(valorOferecido),
      localizacao: `${rua} ${numeroCasa},${bairro},${cidade}, ${estado}`,
    };
    try {
      const response = await axios.post('http://localhost:8080/trabalhos', dadosSolicitacao);
      console.log('Solicitação enviada com sucesso:', response.data);
      fecharModal();
    } catch (error) {
      console.error('Erro ao enviar a solicitação:', error);
    }
  };

  return (
    <div className="div-container-tela-principal">
      <div className="div-nav">
        <div className="div-logo"></div>
        <div className="div-select-nav">
          <form className="div-search">
            <div className="barra-search">
              <div className="fundo-search">
                <input
                  value={inptSearch}
                  onChange={handleSearchChange}
                  type="text"
                  placeholder="Qual nome do trabalhador que você procura?"
                  className="input-tela-principal"
                />
              </div>
            </div>
          </form>
          <div className="div-conta-perfil">
            <div className="div-select-perfil">
              <select
                className="option-padrao"
                onChange={(e) => {
                  if (e.target.value === "perfil") {
                    navigate('/telaperfilcliente');
                  } else if (e.target.value === "sair") {
                    navigate('/login');
                    logout();
                  }
                }}
              >
                <option className="none-here" value=""></option>
                <option value="perfil">Meu Perfil</option>
                <option value="sair">Sair</option>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div className="container-body">
        {trabalhadoresPesquisados.map((trabalhador) => (
          <CardTrabalhador
            key={trabalhador.username}
            username={trabalhador.username}
            localizacao={trabalhador.cep}
            especializacao={trabalhador.especialidade}
            onClick={() => abrirModal(trabalhador, usuario)}
          />
        ))}
      </div>
      <Modal
        isOpen={modalIsOpen}
        onRequestClose={fecharModal}
        contentLabel="Solicitar Serviço"
        className="modal-container"
        overlayClassName="modal-overlay"
      >
        <h2>Solicitar Serviço</h2>
        {trabalhadorSelecionado && (
          <div>
            <h3>Informações do Trabalhador</h3>
            <p><strong>Nome:</strong> {trabalhadorSelecionado.username}</p>
            <p><strong>Localização:</strong> {localizacao}</p>
            <p><strong>Especialização:</strong> {trabalhadorSelecionado.especialidade}</p>
            <h3>Detalhes do Serviço</h3>
            <form>
              <label>Descrição do Problema:</label>
              <textarea
                value={descricaoProblema}
                onChange={(e) => setDescricaoProblema(e.target.value)}
                placeholder="Descreva o problema que precisa ser resolvido..."
              />
              <label>Valor Oferecido:</label>
              <input
                type="number"
                className="inputsModal"
                value={valorOferecido}
                onChange={(e) => setValorOferecido(e.target.value)}
                placeholder="Valor oferecido ao trabalhador"
              />
              <h3>Localização</h3>
              <label>Endereço (Rua):</label>
              <input
                type="text"
                className="inputsModal"
                value={rua}
                onChange={(e) => setRua(e.target.value)}
                placeholder="Rua"
              />
              <label>Bairro:</label>
              <input
                type="text"
                className="inputsModal"
                value={bairro}
                onChange={(e) => setBairro(e.target.value)}
                placeholder="Bairro"
              />
              <label>Cidade:</label>
              <input
                type="text"
                className="inputsModal"
                value={cidade}
                onChange={(e) => setCidade(e.target.value)}
                placeholder="Cidade"
              />
              <label>Estado:</label>
              <input
                type="text"
                className="inputsModal"
                value={estado}
                onChange={(e) => setEstado(e.target.value)}
                placeholder="Estado"
              />
              <label>Número da Casa:</label>
              <input
                type="number"
                className="inputsModal"
                value={numeroCasa}
                onChange={(e) => setNumeroCasa(e.target.value)}
                placeholder="Número da casa"
              />
              <button type="button" onClick={enviarSolicitacao} className="btn-submit">
                Confirmar Solicitação
              </button>
            </form>
          </div>
        )}
        <button onClick={fecharModal} className="btn-close">Fechar</button>
      </Modal>
    </div>
  );
}

export default TelaPrincipal;
