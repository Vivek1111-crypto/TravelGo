import { useEffect, useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";
function Properties() {
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const navigate = useNavigate();
  useEffect(() => {
    const fetchProperties = async () => {
      try {
        const response = await api.get("/api/properties");

        console.log("Properties API response:", response.data);

        setProperties(response.data);
      } catch (error) {
        console.error("Failed to fetch properties:", error);

        setError("Unable to load properties.");
      } finally {
        setLoading(false);
      }
    };

    fetchProperties();
  }, []);

  // Loading state
  if (loading) {
    return (
      <div className="properties-page">
        <h2>Loading properties...</h2>
      </div>
    );
  }

  // Error state
  if (error) {
    return (
      <div className="properties-page">
        <h2>{error}</h2>
      </div>
    );
  }

  return (
    <div className="properties-page">
      <h1>Available Properties</h1>

      <div className="properties-grid">
        {properties.map((property) => (
          <div className="property-card" key={property.id}>
            <h2>{property.name}</h2>
            <p className="description">{property.description}</p>
            <p>
              📍 {property.city}, {property.state}, {property.country}
            </p>
            <p>
              🏠 {property.bedrooms} Bedrooms · {property.bathrooms} Bathrooms
            </p>
            <p>👥 Maximum Guests: {property.maxGuests}</p>
            <p>💰 ₹{property.pricePerNight} / night</p>
            <p>🏠 Address: {property.address}</p>
            <p>✨ Amenities: {property.amenities}</p>
            <p>
              {property.available ? "✅ Available" : "❌ Currently unavailable"}
            </p>
            <button onClick={() => navigate(`/properties/${property.id}`)}>
              View Details
            </button>{" "}
          </div>
        ))}
      </div>
    </div>
  );
}

export default Properties;
