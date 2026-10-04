import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

function AdminDashboard() {
  const { user } = useAuth();
  const navigate = useNavigate();

  return (
    <div className="properties-page">
      <h1>Admin Dashboard</h1>

      <p>Welcome, {user?.firstName}</p>

      <div className="properties-grid">
        <div className="property-card">
          <h2>🏨 Properties</h2>
          <p>Add, update and manage hotel properties.</p>

          <button onClick={() => navigate("/admin/properties")}>
            Manage Properties
          </button>
        </div>

        <div className="property-card">
          <h2>🚪 Rooms</h2>
          <p>Add, update and manage rooms.</p>
          <button onClick={() => navigate("/admin/rooms")}>Manage Rooms</button>
        </div>
      </div>
    </div>
  );
}

export default AdminDashboard;
