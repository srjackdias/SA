import { Navigate } from 'react-router-dom';
import { useUserContext } from '../../context/GlobalContext';

const ProtectedRoute = ({ children }) => {
  const { token } = useUserContext();

  const tokenLocal = localStorage.getItem('token')

  if (!tokenLocal) {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default ProtectedRoute;
