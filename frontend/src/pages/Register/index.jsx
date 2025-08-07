import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../services/api";
import "./index.css";

export default function Register() {
  const [form, setForm] = useState({
    nome: "",
    login: "",
    senha: "",
    cpf: "",
  });
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === "cpf") {
      // Remove all non-numeric characters
      const numericValue = value.replace(/\D/g, "");

      // Apply CPF mask (XXX.XXX.XXX-XX)
      let maskedValue = numericValue;
      if (numericValue.length > 3) {
        maskedValue =
          numericValue.substring(0, 3) + "." + numericValue.substring(3);
      }
      if (numericValue.length > 6) {
        maskedValue =
          maskedValue.substring(0, 7) + "." + maskedValue.substring(7);
      }
      if (numericValue.length > 9) {
        maskedValue =
          maskedValue.substring(0, 11) + "-" + maskedValue.substring(11, 13);
      }

      setForm({ ...form, [name]: maskedValue });
    } else {
      setForm({ ...form, [name]: value });
    }
  };

  const navigate = useNavigate();
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      // Remove mask from CPF before sending to API
      const formData = {
        ...form,
        cpf: form.cpf.replace(/\D/g, ""), // Remove dots and dashes
      };

      await api.post("/usuarios", formData);
      alert("Usuário cadastrado com sucesso!");
      setForm({
        nome: "",
        login: "",
        senha: "",
        cpf: "",
      });

      navigate("/login");
    } catch (err) {
      alert("Erro ao cadastrar usuário.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="registerDiv">
      <form onSubmit={handleSubmit}>
        <h1>Cadastre-se</h1>
        <input
          name="nome"
          placeholder="Nome Completo"
          onChange={handleChange}
        />
        <input name="login" placeholder="Email" onChange={handleChange} />
        <input
          name="senha"
          placeholder="Senha"
          onChange={handleChange}
          type="password"
        />
        <input
          name="cpf"
          placeholder="CPF (000.000.000-00)"
          onChange={handleChange}
          value={form.cpf}
          maxLength="14"
        />
        <button type="submit" disabled={loading}>
          {loading ? "Cadastrando..." : "Cadastrar"}
        </button>
        <button type="button" onClick={() => navigate("/login")}>
          Voltar
        </button>
      </form>
    </div>
  );
}
