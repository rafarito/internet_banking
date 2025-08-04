import { useState } from "react";
import api from "../services/api";

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
    <form onSubmit={handleSubmit}>
      <input name="nome" placeholder="Nome" onChange={handleChange} />
      <input name="login" placeholder="Login" onChange={handleChange} />
      <input
        name="senha"
        placeholder="Senha"
        onChange={handleChange}
        type="password"
      />
      <input name="cpf" placeholder="CPF" onChange={handleChange} />
      <button type="submit">Cadastrar</button>
    </form>
  );
}
