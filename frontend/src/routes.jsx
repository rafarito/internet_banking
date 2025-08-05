import { BrowserRouter, Routes, Route } from "react-router-dom";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Extrato from "./pages/extrato";
import Deposito from "./pages/Deposito";
import Saque from "./pages/Saque";
import Pagamento from "./pages/Pagamento";
import ProtectedRoute from "./components/ProtectedRoute";

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login/>}/>
        <Route path="/cadastro" element={<Register/>}/>
        <Route path="/" element={
          <ProtectedRoute>
            <Dashboard />
          </ProtectedRoute>
        } />
        <Route path="/extrato" element={
          <ProtectedRoute>
            <Extrato/>
          </ProtectedRoute>
        }/>
        <Route path="/deposito" element={
          <ProtectedRoute>
            <Deposito/>
          </ProtectedRoute>
        }/>
        <Route path="/saque" element={
          <ProtectedRoute>
            <Saque/>
          </ProtectedRoute>
        }/>
        <Route path="/pagamento" element={
          <ProtectedRoute>
            <Pagamento/>
          </ProtectedRoute>
        }/>
      </Routes>
    </BrowserRouter>
  );
}
