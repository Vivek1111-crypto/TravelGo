import "./App.css";
import Navbar from "./components/Navbar";
import AddRoom from "./pages/AddRoom";
import { BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";
import ManageProperties from "./pages/ManageProperties";
import EditProperty from "./pages/EditProperty";
import Login from "./pages/Login";
import { AuthProvider, useAuth } from "./context/AuthContext";
import Properties from "./pages/Properties";
import PropertyDetails from "./pages/PropertyDetails";
import EditRoom from "./pages/EditRoom";
import MyBookings from "./pages/MyBookings";
import Payment from "./pages/Payment";
import Booking from "./pages/Booking";
import AdminDashboard from "./pages/AdminDashboard";
import AddProperty from "./pages/AddProperty";
import ManageRooms from "./pages/ManageRooms";
// -----------------------------
// Main application content
// -----------------------------
function AdminRoute({ children }) {
  const { user } = useAuth();

  if (!user) {
    return <h2>Please login to access the admin dashboard.</h2>;
  }

  if (user.role !== "ADMIN") {
    return <h2>Access denied. Admin only.</h2>;
  }

  return children;
}
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
        <Route
          path="/admin"
          element={
            <AdminRoute>
              <AdminDashboard />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/properties/add"
          element={
            <AdminRoute>
              <AddProperty />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/rooms/add"
          element={
            <AdminRoute>
              <AddRoom />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/properties"
          element={
            <AdminRoute>
              <ManageProperties />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/properties/edit/:id"
          element={
            <AdminRoute>
              <EditProperty />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/rooms"
          element={
            <AdminRoute>
              <ManageRooms />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/rooms/edit/:id"
          element={
            <AdminRoute>
              <EditRoom />
            </AdminRoute>
          }
        />
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
