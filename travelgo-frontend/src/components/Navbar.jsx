import { Link } from "react-router-dom";

function Navbar() {
  return (
    <nav className="navbar">
      <div className="logo">TravelGo</div>

      <div className="nav-links">
        <Link to="/">Home</Link>
        <Link to="/properties">Properties</Link>
        <Link to="/bookings">My Bookings</Link>
        <Link to="/login">Login</Link>
      </div>
    </nav>
  );
}

export default Navbar;
