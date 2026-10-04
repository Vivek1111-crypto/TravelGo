import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <nav className="navbar">
      <div className="logo">TravelGo</div>

      <div className="nav-links">
        <Link to="/">Home</Link>

        <Link to="/properties">Properties</Link>

        {user && <Link to="/bookings">My Bookings</Link>}

        {user?.role === "ADMIN" && <Link to="/admin">Admin</Link>}

        {!user ? (
          <Link to="/login">Login</Link>
        ) : (
          <button onClick={handleLogout} className="logout-btn">
            Logout
          </button>
        )}
      </div>
    </nav>
  );
}

export default Navbar;
