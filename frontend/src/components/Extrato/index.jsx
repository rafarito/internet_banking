import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../services/api";
import "./index.css";

export default function Extrato() {
  const navigate = useNavigate();
  const [extrato, setExtrato] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchExtrato = async () => {
      try {
        const token = localStorage.getItem('token');
        
        if (!token) {
          alert('Token não encontrado. Faça login novamente.');
          navigate('/login');
          setLoading(false);
          return;
        }

        const response = await api.get('/operacao/extrato', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });

        setExtrato(response.data);
      } catch (err) {
        alert('Erro ao carregar extrato.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchExtrato();
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  if (loading) return (
    <div className="extrato-page">
      <div className="loading-container">Carregando...</div>
    </div>
  );

  return (
    <div className="extrato-page">
      <header className="extrato-header">
        <div className="header-content">
          <button onClick={() => navigate('/')} className="back-btn">
            ← Voltar ao Dashboard
          </button>
          <h1>Extrato Completo</h1>
          <button onClick={handleLogout} className="logout-btn">Sair</button>
        </div>
      </header>

      <main className="extrato-main">
        <div className="extrato-container">
          {extrato.length > 0 ? (
            <div className="extrato-list">
              {extrato.map((operacao) => (
                <div key={operacao.id} className="extrato-item" data-tipo={operacao.tipo}>
                  <div className="extrato-header-item">
                    <span className="extrato-id">#{operacao.id}</span>
                    <span className="extrato-tipo">{operacao.tipo}</span>
                  </div>
                  <div className="extrato-content">
                    <div className="extrato-info">
                      <p className="extrato-descricao">
                        {operacao.descricao || 'Sem descrição'}
                      </p>
                      <p className="extrato-data">
                        {new Date(operacao.dataHora).toLocaleString('pt-BR')}
                      </p>
                    </div>
                    <div className="extrato-valor">
                      R$ {operacao.valor.toFixed(2)}
                    </div>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div className="no-transactions">
              <h2>Nenhuma transação encontrada</h2>
              <p>Suas transações aparecerão aqui quando você realizar operações.</p>
            </div>
          )}
        </div>
      </main>
    </div>
  );
}