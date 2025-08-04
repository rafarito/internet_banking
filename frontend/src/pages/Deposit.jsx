// src/pages/Deposit.jsx, Withdraw.jsx, Payment.jsx
import { useState } from "react";
import api from "../services/api";

export default function Deposit() {
  const [form, setForm] = useState({
    numeroConta: "",
    valor: "",
    descricao: "",
  });

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post("/operacao/deposito", {
        ...form,
        valor: parseFloat(form.valor),
        numeroConta: parseInt(form.numeroConta),
      });
      alert("Depósito realizado!");
    } catch (err) {
      alert("Erro ao realizar depósito.");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input name="numeroConta" placeholder="Conta" onChange={handleChange} />
      <input
        name="valor"
        placeholder="Valor"
        type="number"
        onChange={handleChange}
      />
      <input name="descricao" placeholder="Descrição" onChange={handleChange} />
      <button type="submit">Depositar</button>
    </form>
  );
}
