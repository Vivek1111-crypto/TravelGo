import "./App.css";
import Navbar from "./components/Navbar";

import { BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";

import Login from "./pages/Login";
import { AuthProvider } from "./context/AuthContext";
import Properties from "./pages/Properties";
import PropertyDetails from "./pages/PropertyDetails";
import MyBookings from "./pages/MyBookings";
import Payment from "./pages/Payment";
import Booking from "./pages/Booking";

// -----------------------------
// Main application content
// -----------------------------
function AppContent() {
  const navigate = useNavigate();

  return (
    <>
      <Navbar />

      <Routes>
        {/* Home */}
        <Route
          path="/"
          element={
            <section className="hero">
              <div className="hero-content">
                <h1>Find Your Perfect Stay</h1>

                <p>
                  Discover comfortable hotels and rooms for your next journey.
                </p>

                <button
                  className="explore-btn"
                  onClick={() => navigate("/properties")}
                >
                  Explore Properties
                </button>
              </div>
            </section>
          }
        />

        {/* Login */}
        <Route path="/login" element={<Login />} />

        {/* Properties */}
        <Route path="/properties" element={<Properties />} />

        {/* Property Details */}
        <Route path="/properties/:id" element={<PropertyDetails />} />

        {/* Booking */}
        <Route path="/booking" element={<Booking />} />

        {/* My Bookings */}
        <Route path="/bookings" element={<MyBookings />} />

        {/* Payment */}
        <Route path="/payment" element={<Payment />} />
      </Routes>
    </>
  );
}

// -----------------------------
// App
// -----------------------------
function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <AppContent />
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
