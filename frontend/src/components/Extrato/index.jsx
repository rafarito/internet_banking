import { useState, useEffect } from "react";
import api from "../../services/api";

export default function Extrato() {
  const [extrato, setExtrato] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchExtrato = async () => {
      try {
        const token = localStorage.getItem('token');
        
        if (!token) {
          alert('Token não encontrado. Faça login novamente.');
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
  }, []);

  if (loading) return <div>Carregando...</div>;

  return (
    <div>
      <h1>Extrato</h1>
      <div>
        {extrato.length > 0 ? (
          extrato.map((operacao) => (
            <div key={operacao.id}>
              <p><strong>ID:</strong> {operacao.id}</p>
              <p><strong>Tipo:</strong> {operacao.tipo}</p>
              <p><strong>Valor:</strong> R$ {operacao.valor.toFixed(2)}</p>
              <p><strong>Data/Hora:</strong> {new Date(operacao.dataHora).toLocaleString()}</p>
              <p><strong>Descrição:</strong> {operacao.descricao || 'Sem descrição'}</p>
              <hr />
            </div>
          ))
        ) : (
          <p>Nenhuma operação encontrada.</p>
        )}
      </div>
    </div>
  );
}