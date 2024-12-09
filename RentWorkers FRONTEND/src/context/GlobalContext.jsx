import React, { createContext, useState, useContext, useEffect } from 'react';
import { dadosUsuarioLogado } from '../config/axios';
import { useNavigate } from 'react-router-dom';

// Criação do contexto
export const UserContext = createContext();

// Componente Provider
export const UserProvider = ({ children }) => {


  const [token, setToken] = useState();
  const [usuario, setUsuario] = useState();
  const [count, setCount] = useState(0)




  const login = (jwtToken, usuario) => {
    setToken(jwtToken);
    localStorage.setItem("token", jwtToken);
    setUsuario(usuario)
    localStorage.setItem("usuario", JSON.stringify(usuario));
  };

  const logout = () => {
    setToken(null);
    setUsuario(null);
    localStorage.clear();
  };

  useEffect(() => {



    if (localStorage.getItem('token') != null && localStorage.getItem('usuario') != null) {
      const storageToken = localStorage.getItem('token');
      const storageUsuario = JSON.parse(localStorage.getItem('usuario'))
      setToken(storageToken);
      setUsuario(storageUsuario);
      if (count < 3) {
        dadosUsuarioLogado(storageUsuario.id_usuario)
          .then((response) => {
            setUsuario(response.data);
            setCount(count + 1);
            console.log("Chegou aqui")
            localStorage.setItem("usuario", JSON.stringify(response.data));
          })
          .catch((error) => {
            console.error("Não existe um usuario com este ID", error);
          });
      }
    }






  }, [count]);





  return (
    <UserContext.Provider value={{ token, login, logout, usuario }}>
      {children}
    </UserContext.Provider>
  );
};


export const useUserContext = () => useContext(UserContext);
