import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import './index.css';

import PaginaHome from './componentes/TelaHome/PaginaHome.jsx';
import TelaLogin from './componentes/TelaLogin/TelaLogin.jsx';
import TelaCadastro from './componentes/TelaCadastro/TelaCadastro.jsx';
import TelaPrincipal from './componentes/TelaPrincipal/TelaPrincipal.jsx';
import Perfil from './componentes/TelaPerfil/Perfil.jsx';
import TelaPerfilCliente from './componentes/TelaPerfilCliente/TelaPerfilCliente.jsx';

import Cliente from './componentes/TelaCliente/Cliente.jsx';

import ProtectedRoute from './componentes/verificacao/ProtectedRoute.jsx';

const router = createBrowserRouter([
  {
    path: "/",
    element: <PaginaHome />,
  },
  {
    path: "/telaprincipal",
    element: (
      <ProtectedRoute>
        <TelaPrincipal />
      </ProtectedRoute>
    ),
  },
  {
    path: "/perfil",
    element: (
      <ProtectedRoute>
        <Perfil />
      </ProtectedRoute>
    ),
  },
  {
    path: "/cliente",
    element: (
      <ProtectedRoute>
        <Cliente />
      </ProtectedRoute>
    ),
  },
  {
    path: "/login",
    element: <TelaLogin />,
  },
  {
    path: "/cadastro",
    element: <TelaCadastro />,
  },
  {
    path: "/telaperfilcliente",
    element: <TelaPerfilCliente />
  },
  {
    path: "/telatrabalhador",
    element: (
      <ProtectedRoute>
        <TelaTrabalhador />
      </ProtectedRoute>
    ),
  }
]);

import { UserProvider } from './context/GlobalContext.jsx';
import TelaTrabalhador from './componentes/TelaTrabalhador/TelaTrabalhador.jsx';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <UserProvider>
      <RouterProvider router={router} />
    </UserProvider>
  </StrictMode>
);
