import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../services/api";
import "./index.css";

export default function Deposito() {
  const navigate = useNavigate();
  const [valor, setValor] = useState("");
  const [loading, setLoading] = useState(false);

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

      await api.post("/operacao/deposito", {
        numeroConta: 0,
        valor: parseFloat(valor),
        descricao: "string"
      }, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      
      alert("Depósito realizado com sucesso!");
      setValor("");
    } catch (err) {
      alert("Erro ao realizar depósito.");
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
    <div className="deposito-page">
      <header className="deposito-header">
        <div className="header-content">
          <button onClick={() => navigate('/')} className="back-btn">
            ← Voltar ao Dashboard
          </button>
          <h1>Depósito</h1>
          <button onClick={handleLogout} className="logout-btn">Sair</button>
        </div>
      </header>

      <main className="deposito-main">
        <div className="deposito-container">
          <div className="deposito-card">
            <h2>Realizar Depósito</h2>
            <p className="deposito-description">
              Insira o valor que deseja depositar em sua conta
            </p>
            
            <form onSubmit={handleSubmit} className="deposito-form">
              <div className="input-group">
                <label htmlFor="valor">Valor do Depósito</label>
                <input
                  id="valor"
                  placeholder="R$ 0,00"
                  type="number"
                  step="0.01"
                  min="0.01"
                  value={valor}
                  onChange={(e) => setValor(e.target.value)}
                  required
                  className="valor-input"
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
                    💰 Depositar
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
