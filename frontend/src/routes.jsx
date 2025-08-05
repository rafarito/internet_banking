import { BrowserRouter, Routes, Route } from "react-router-dom";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Extrato from "./pages/extrato";
import Deposito from "./pages/Deposito";
import Saque from "./pages/Saque";
import Pagamento from "./pages/Pagamento";
// import Deposit from "./pages/Deposit";
// import Saque from "./pages/Saque";
// import Payment from "./pages/Payment";
// import Statement from "./pages/Statement";

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/login" element={<Login/>}/>
        <Route path="/cadastro" element={<Register/>}/>
        <Route path="/extrato" element={<Extrato/>}/>
        <Route path="/deposito" element={<Deposito/>}/>
        <Route path="/saque" element={<Saque/>}/>
        <Route path="/pagamento" element={<Pagamento/>}/>
      </Routes>
    </BrowserRouter>
  );
}
// <Route path="/register" element={<Register />} />
// <Route path="/login" element={<Login />} />
// <Route path="/deposit" element={<Deposit />} />
// <Route path="/saque" element={<Saque />} />
// <Route path="/payment" element={<Payment />} />
// <Route path="/statement" element={<Statement />} />
