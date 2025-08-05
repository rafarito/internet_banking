import { useState } from "react";
import api from "../services/api";

export default function Saque() {
  const [valor, setValor] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const token = localStorage.getItem('token');
      
      if (!token) {
        alert('Token não encontrado. Faça login novamente.');
        setLoading(false);
        return;
      }

      await api.post("/operacao/saque", {
        numeroConta: 0,
        valor: parseFloat(valor),
        descricao: "string"
      }, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      
      alert("Saque realizado com sucesso!");
      setValor("");
    } catch (err) {
      alert("Erro ao realizar saque.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h1>Saque</h1>
      <input
        placeholder="Valor"
        type="number"
        step="0.01"
        min="0"
        value={valor}
        onChange={(e) => setValor(e.target.value)}
        required
      />
      <button type="submit" disabled={loading}>
        {loading ? "Processando..." : "Sacar"}
      </button>
    </form>
  );
}
