import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../services/api";
import "./index.css";

export default function Dashboard() {
  const navigate = useNavigate();
  const [extrato, setExtrato] = useState([]);
  const [loading, setLoading] = useState(true);
  const [userName, setUserName] = useState("");

  useEffect(() => {
    const fetchExtrato = async () => {
      try {
        const token = localStorage.getItem('token');
        
        if (!token) {
          navigate('/login');
          return;
        }

        const response = await api.get('/operacao/extrato', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });

        setExtrato(response.data);
      } catch (err) {
        console.error('Erro ao carregar extrato:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchExtrato();
    const interval = setInterval(fetchExtrato, 30000);
    
    return () => clearInterval(interval);
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <h1>Internet Chair</h1>
        <div className="header-actions">
          <span>Bem-vindo!</span>
          <button onClick={handleLogout} className="logout-btn">Sair</button>
        </div>
      </header>

      <main className="dashboard-main">
        <section className="operations-section">
          <h2>Operações</h2>
          <div className="operations-grid">
            <div className="operation-card" onClick={() => navigate("/deposito")}>
              <h3>💰 Depósito</h3>
              <p>Depositar dinheiro na conta</p>
            </div>
            
            <div className="operation-card" onClick={() => navigate("/saque")}>
              <h3>💳 Saque</h3>
              <p>Sacar dinheiro da conta</p>
            </div>
            
            <div className="operation-card" onClick={() => navigate("/pagamento")}>
              <h3>💸 Pagamento</h3>
              <p>Realizar pagamentos</p>
            </div>
            
            <div className="operation-card" onClick={() => navigate("/extrato")}>
              <h3>📄 Extrato Completo</h3>
              <p>Ver extrato detalhado</p>
            </div>
          </div>
        </section>

        <section className="extrato-section">
          <h2>Últimas Transações</h2>
          <div className="extrato-container">
            {loading ? (
              <div className="loading">Carregando transações...</div>
            ) : extrato.length > 0 ? (
              <div className="extrato-list">
                {extrato.slice(0, 15).map((operacao) => (
                  <div key={operacao.id} className="extrato-item">
                    <div className="extrato-info">
                      <span className="extrato-tipo">{operacao.tipo}</span>
                      <span className="extrato-descricao">
                        {operacao.descricao || 'Sem descrição'}
                      </span>
                    </div>
                    <div className="extrato-details">
                      <span className="extrato-valor">
                        R$ {operacao.valor.toFixed(2)}
                      </span>
                      <span className="extrato-data">
                        {new Date(operacao.dataHora).toLocaleDateString()}
                      </span>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <div className="no-transactions">Nenhuma transação encontrada</div>
            )}
          </div>
        </section>
      </main>
    </div>
  );
}
