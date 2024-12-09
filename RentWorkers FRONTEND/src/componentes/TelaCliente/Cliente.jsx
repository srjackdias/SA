import React, { useState, useContext,useEffect } from 'react';
import "./Cliente.css";
import { UserContext } from '../../context/GlobalContext';

const Cliente = () => {
    const { usuario, logout } = useContext(UserContext);
    const [inputContaOferta, setInputContraOferta] = useState('');
    const [erroContraOferta, setErroContraOferta] = useState('');
    const [sucesso, setSucesso] = useState('');
    const [contraOferta, setContraOfertas] = useState([]);

  



    function enviarContraOferta() {
        if (!inputContaOferta) {
            setErroContraOferta('O valor da contra oferta está vazio');
            setSucesso('');
        } else if (isNaN(inputContaOferta)) {
            setErroContraOferta('A contra oferta deve ser um número válido');
            setSucesso('');
        } else {
            setContraOfertas([...contraOferta, inputContaOferta]);

            setInputContraOferta('');
            setErroContraOferta('');

            setSucesso(`Contra oferta enviada com sucesso: R$${inputContaOferta}`);

            console.log("Contra ofertas enviadas:", [...contraOferta, inputContaOferta]);
        }
    }

    function aceitarOferta() {
        if (!inputContaOferta) {
            setErroContraOferta('Você precisa fazer uma contra oferta antes de aceitar.');
            setSucesso('');
        } else {
            setSucesso(`Você aceitou a contra oferta: R$${inputContaOferta}`);
            setErroContraOferta('');
            console.log("Oferta aceita com sucesso:", inputContaOferta);
        }
    }

    function rejeitarOferta() {
        setSucesso('');
        setErroContraOferta('Você rejeitou a oferta.');
        setInputContraOferta(''); 
        console.log("Oferta rejeitada.");
    }

    return (
        <div className='Container-cliente'>
            <div className='divPainel-cliente'></div>

            <div className='divCliente'>
                <div className='div-cima-cliente'>
                    <img
                        className='imgPerfilum-cliente'
                        src='/images/download 46 (1).png'
                        alt="Perfil"
                    />
                    <p className='fonteNome-cliente'>{usuario?.username || 'Usuário não identificado'}</p>
                </div>

                <div className='divCliente-Esquerda'>
                    <div className='div-dados-cliente'>
                        <p>Contato</p>
                    </div>
                    <div className='div-nota-cliente'>
                        <p>Nota e Avaliações</p>
                    </div>
                    <div className='div-solicitacoes-cliente'>
                        <p>Solicitações de trabalho</p>
                    </div>
                    <div className='div-vazio-cliente'></div>
                    <div className='div-Sair-cliente'>
                        <p onClick={logout} className="logout-link">Sair</p>
                    </div>
                </div>

                <div className='div-meio-cliente'>
                    <div className='div-Pedidos'>
                        <p className='FontePedido'>Pedidos de Trabalho</p>
                    </div>
                    <hr />

                    <div className='div-form-cliente'>
                        <div className='divAjuda'>
                            <p className='fonteNome'>Cliente: Josilei</p>
                            <p className='fonteAjuda'>
                                Tenho um vazamento no cano da pia da cozinha. A água está escorrendo e molhando o chão, mesmo depois de tentar vedar. Preciso de um encanador para resolver isso rápido.
                            </p>
                        </div>
                    </div>

                    <div className='div-form-clienteDois'>
                        <div className='div-form-botoes'>
                            <button className='BotaoAceitar' onClick={aceitarOferta}>Aceitar</button>
                            <button className='BotaoRejeitar' onClick={rejeitarOferta}>Rejeitar</button>
                        </div>

                        <div className='div-form-botoesDois'>
                            <p className='fonteOferta'>Oferta R$:</p>
                            <p className='fonteOContraferta'>ContraOferta R$:</p>
                        </div>

                        <div className='div-form-botoesTres'>
                            <input
                                className='inputLouco'
                                type="number"
                                value={inputContaOferta}
                                onChange={(event) => setInputContraOferta(event.target.value)}
                            />
                            <button onClick={enviarContraOferta} className='ButtonOferta'>Enviar</button>
                        </div>
                    </div>
                    <div>
                        {erroContraOferta && <p className='erromensagem'>{erroContraOferta}</p>}
                        {sucesso && <p className="sucessoMensagem">{sucesso}</p>}
                    </div>
                    <hr />
                </div>
            </div>
        </div>
    );
};

export default Cliente;
