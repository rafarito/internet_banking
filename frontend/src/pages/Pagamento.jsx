import { useState } from "react";
import api from "../services/api";

export default function Pagamento() {
  const [form, setForm] = useState({
    numeroConta: "",
    valor: "",
    descricao: "",
  });
  const [loading, setLoading] = useState(false);

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

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

      await api.post("/operacao/pagamento", {
        numeroConta: parseInt(form.numeroConta),
        valor: parseFloat(form.valor),
        descricao: form.descricao
      }, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      
      alert("Pagamento realizado com sucesso!");
      setForm({
        numeroConta: "",
        valor: "",
        descricao: "",
      });
    } catch (err) {
      alert("Erro ao realizar pagamento.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h1>Pagamento</h1>
      <input
        name="numeroConta"
        placeholder="Número da Conta"
        type="number"
        value={form.numeroConta}
        onChange={handleChange}
        required
      />
      <input
        name="valor"
        placeholder="Valor"
        type="number"
        step="0.01"
        min="0"
        value={form.valor}
        onChange={handleChange}
        required
      />
      <input
        name="descricao"
        placeholder="Descrição"
        value={form.descricao}
        onChange={handleChange}
        required
      />
      <button type="submit" disabled={loading}>
        {loading ? "Processando..." : "Pagar"}
      </button>
    </form>
  );
}
