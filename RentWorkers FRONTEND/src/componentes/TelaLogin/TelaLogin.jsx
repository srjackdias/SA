import { Link, useNavigate } from 'react-router-dom';
import '../TelaLogin/Login.css';
import { useState, useEffect, useContext } from "react";
import { UserContext } from '../../context/GlobalContext';
import { listaUsuarios } from '../../config/axios';
import api from '../../config/axios';



function TelaLogin() {
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setSenha] = useState('');
    const { login } = useContext(UserContext);

    const [usuarios, setUsuarios] = useState([]);



    useEffect(() => {
        listaUsuarios().then((response) => {
            setUsuarios(response.data);
        }).catch(error => {
            console.log(error);
        })
    })

    const handleLogin = async (e) => {

        e.preventDefault();
        const loginData = {
            email: email,
            password: password
        };

        if (loginData.email == "" || loginData.password == "") {
            alert("Preencha todos os campos");
        } else {
            try {
                const response = await api.post('/auth/login', loginData);
                login(response.data.token, response.data.usuario);
                const usuario = response.data.usuario;
                console.log(usuario)
                if(usuario.tipoUsuario == "CLIENTE") {
                    navigate("/telaprincipal")
                    window.location.reload()
                }else {
                    navigate("/perfil")
                    window.location.reload()
                }
                
            } catch (error) {
                alert("Usuario não existe")
                setSenha("")
            }
        }


    };



    return (
        <div className="containerLogin">
            <div className="divImagem">
                <img className='imgStyle' src='/images/imagemLogin.jpg' alt="Login" />
            </div>
            <div className="divFormLogin">
                <form className='divForm' onSubmit={handleLogin}>
                    <div className='divTitulo'>
                        <h1>Faça o seu login</h1>
                    </div>
                    <div className='divSubTitulo'>
                        <label>Digite seus dados de acesso no campo abaixo.</label>
                    </div>
                    <div className='divEmail'>
                        <input
                            className='inptLogin'
                            type="text"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="Email"
                        />
                    </div>
                    <div className='divSenha'>
                        <input
                            className='inptLogin'
                            type="password"
                            value={password}
                            onChange={(e) => setSenha(e.target.value)}
                            placeholder="Senha"
                        />
                    </div>
                    <div className='divButtonLogin'>
                        <button className="buttonStyle" type="submit">Entrar</button>
                        <p>Não possui uma conta? <Link to="/cadastro">Cadastre-se</Link></p>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default TelaLogin;
