import { useNavigate } from "react-router-dom";

export default function Dashboard() {
  const navigate = useNavigate();

  return (
    <div>
      <h1>Bem-vindo ao Internet Banking</h1>
      <button onClick={() => navigate("/login")}>Login</button>
    </div>
  );
}
