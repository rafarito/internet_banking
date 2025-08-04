import { useState } from "react";
import api from "../services/api";

export default function Login() {
  const [login, setLogin] = useState("");
  const [senha, setSenha] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post("/login", { login, senha });
      alert("Login realizado com sucesso!");
      // você pode armazenar os dados do usuário ou token no localStorage
    } catch (err) {
      alert("Login inválido!");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input placeholder="Login" onChange={(e) => setLogin(e.target.value)} />
      <input
        placeholder="Senha"
        type="password"
        onChange={(e) => setSenha(e.target.value)}
      />
      <button type="submit">Entrar</button>
    </form>
  );
}
