import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../services/api";
import "./index.css";

export default function Login() {
  const [login, setLogin] = useState("");
  const [senha, setSenha] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post("/login", { login, senha });
      alert("Login realizado com sucesso!");
      localStorage.setItem("token", response.data.token);
      navigate("/");
    } catch (err) {
      alert("Login inválido!");
    }
  };

  return (
    <div className="loginDiv">
      <form onSubmit={handleSubmit}>
        <h1>Internet Chair</h1>
        <input
          placeholder="E-mail"
          onChange={(e) => setLogin(e.target.value)}
        />
        <input
          placeholder="Senha"
          type="password"
          onChange={(e) => setSenha(e.target.value)}
        />
        <button type="submit">Entrar</button>
        <button type="button" onClick={() => navigate("/cadastro")}>
          Não tem conta? Cadastre-se
        </button>
      </form>
    </div>
  );
}
