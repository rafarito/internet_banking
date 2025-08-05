import { useState } from "react";
import api from "../../services/api";
import "./index.css";

export default function Register() {
  const [form, setForm] = useState({
    nome: "",
    login: "",
    senha: "",
    cpf: "",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post("/usuarios", form);
      alert("Usuário cadastrado com sucesso!");
    } catch (err) {
      alert("Erro ao cadastrar usuário.");
    }
  };

  return (
    <div className="registerDiv">
      <form onSubmit={handleSubmit}>
        <h1>Cadastre-se</h1>
        <input name="nome" placeholder="Nome Completo" onChange={handleChange} />
        <input name="login" placeholder="Email" onChange={handleChange} />
        <input
          name="senha"
          placeholder="Senha"
          onChange={handleChange}
          type="password"
        />
        <input name="cpf" placeholder="CPF" onChange={handleChange} />
        <button type="submit">Cadastrar</button>
      </form>
    </div>
  );
}
