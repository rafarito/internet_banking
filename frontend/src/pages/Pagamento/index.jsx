import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../services/api";
import "./index.css";

export default function Pagamento() {
  const navigate = useNavigate();
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
        navigate('/login');
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

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  return (
    <div className="pagamento-page">
      <header className="pagamento-header">
        <div className="header-content">
          <button onClick={() => navigate('/')} className="back-btn">
            ← Voltar ao Dashboard
          </button>
          <h1>Pagamento</h1>
          <button onClick={handleLogout} className="logout-btn">Sair</button>
        </div>
      </header>

      <main className="pagamento-main">
        <div className="pagamento-container">
          <div className="pagamento-card">
            <h2>Realizar Pagamento</h2>
            <p className="pagamento-description">
              Preencha os dados para realizar um pagamento
            </p>
            
            <form onSubmit={handleSubmit} className="pagamento-form">
              <div className="input-group">
                <label htmlFor="numeroConta">Número da Conta Destino</label>
                <input
                  id="numeroConta"
                  name="numeroConta"
                  placeholder="Ex: 12345"
                  type="number"
                  value={form.numeroConta}
                  onChange={handleChange}
                  required
                  className="conta-input"
                />
              </div>

              <div className="input-group">
                <label htmlFor="valor">Valor do Pagamento</label>
                <input
                  id="valor"
                  name="valor"
                  placeholder="R$ 0,00"
                  type="number"
                  step="0.01"
                  min="0.01"
                  value={form.valor}
                  onChange={handleChange}
                  required
                  className="valor-input"
                />
              </div>

              <div className="input-group">
                <label htmlFor="descricao">Descrição do Pagamento</label>
                <input
                  id="descricao"
                  name="descricao"
                  placeholder="Ex: Pagamento de conta"
                  value={form.descricao}
                  onChange={handleChange}
                  required
                  className="descricao-input"
                />
              </div>
              
              <button type="submit" disabled={loading} className="submit-btn">
                {loading ? (
                  <>
                    <span className="spinner"></span>
                    Processando...
                  </>
                ) : (
                  <>
                    💸 Realizar Pagamento
                  </>
                )}
              </button>
            </form>
          </div>
        </div>
      </main>
    </div>
  );
}
