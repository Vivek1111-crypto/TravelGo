import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function ManageProperties() {
  const navigate = useNavigate();

  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchProperties = async () => {
    try {
      setLoading(true);
      setError("");

      const response = await api.get("/api/properties");

      console.log("Admin properties:", response.data);

      setProperties(response.data);
    } catch (error) {
      console.error("Failed to fetch properties:", error);

      setError(error.response?.data?.message || "Failed to load properties");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProperties();
  }, []);

  if (loading) {
    return (
      <div className="properties-page">
        <h2>Loading properties...</h2>
      </div>
    );
  }

  if (error) {
    return (
      <div className="properties-page">
        <h2>{error}</h2>
      </div>
    );
  }

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this property?",
    );

    if (!confirmed) {
      return;
    }

    try {
      await api.delete(`/api/properties/${id}`);

      alert("Property deleted successfully!");

      fetchProperties();
    } catch (error) {
      console.error("Failed to delete property:", error);

      alert(error.response?.data?.message || "Failed to delete property");
    }
  };

  return (
    <div className="properties-page">
      <h1>Manage Properties</h1>

      <button onClick={() => navigate("/admin/properties/add")}>
        + Add Property
      </button>

      <div className="properties-grid">
        {properties.map((property) => (
          <div className="property-card" key={property.id}>
            <h2>{property.name}</h2>

            <p>{property.description}</p>

            <p>
              📍 {property.city}, {property.state}
            </p>

            <p>💰 ₹{property.pricePerNight} / night</p>

            <p>👥 Maximum Guests: {property.maxGuests}</p>

            <p>{property.available ? "✅ Available" : "❌ Unavailable"}</p>

            <button
              onClick={() => navigate(`/admin/properties/edit/${property.id}`)}
            >
              Edit
            </button>

            <button onClick={() => handleDelete(property.id)}>Delete</button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ManageProperties;
