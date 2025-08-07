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
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const validateCPF = (cpf) => {
    cpf = cpf.replace(/\D/g, "");
    if (cpf.length !== 11 || /^(\d)\1{10}$/.test(cpf)) return false;
    let soma = 0,
      resto;

    for (let i = 1; i <= 9; i++) soma += parseInt(cpf[i - 1]) * (11 - i);
    resto = (soma * 10) % 11;
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf[9])) return false;

    soma = 0;
    for (let i = 1; i <= 10; i++) soma += parseInt(cpf[i - 1]) * (12 - i);
    resto = (soma * 10) % 11;
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf[10])) return false;

    return true;
  };

  const validateForm = () => {
    const newErrors = {};
    if (!form.nome.trim()) newErrors.nome = "Nome é obrigatório.";
    if (!form.login.match(/^[^\s@]+@[^\s@]+\.[^\s@]+$/))
      newErrors.login = "Email inválido.";
    if (form.senha.length < 6)
      newErrors.senha = "A senha deve ter pelo menos 6 caracteres.";
    if (!validateCPF(form.cpf)) newErrors.cpf = "CPF inválido.";

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === "cpf") {
      const numericValue = value.replace(/\D/g, "");
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

    // Limpar erro ao digitar
    setErrors({ ...errors, [name]: "" });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateForm()) return;

    setLoading(true);
    try {
      const formData = {
        ...form,
        cpf: form.cpf.replace(/\D/g, ""),
      };

      await api.post("/usuarios", formData);
      alert("Usuário cadastrado com sucesso!");
      setForm({ nome: "", login: "", senha: "", cpf: "" });
      navigate("/login");
    } catch (err) {
      alert("Erro ao cadastrar usuário. Dados inválido ou usuário já existe.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="registerDiv">
      <form onSubmit={handleSubmit} noValidate>
        <h1>Cadastre-se</h1>

        <input
          name="nome"
          placeholder="Nome Completo"
          onChange={handleChange}
          value={form.nome}
          className={errors.nome ? "input-error" : ""}
        />
        {errors.nome && <p className="error-message">{errors.nome}</p>}

        <input
          name="login"
          placeholder="Email"
          onChange={handleChange}
          value={form.login}
          className={errors.login ? "input-error" : ""}
        />
        {errors.login && <p className="error-message">{errors.login}</p>}

        <input
          name="senha"
          placeholder="Senha"
          onChange={handleChange}
          type="password"
          value={form.senha}
          className={errors.senha ? "input-error" : ""}
        />
        {errors.senha && <p className="error-message">{errors.senha}</p>}

        <input
          name="cpf"
          placeholder="CPF (000.000.000-00)"
          onChange={handleChange}
          value={form.cpf}
          maxLength="14"
          className={errors.cpf ? "input-error" : ""}
        />
        {errors.cpf && <p className="error-message">{errors.cpf}</p>}

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
