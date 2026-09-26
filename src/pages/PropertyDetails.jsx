import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../services/api";

function PropertyDetails() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [property, setProperty] = useState(null);
  const [rooms, setRooms] = useState([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchPropertyDetails = async () => {
      try {
        // Fetch property details
        const propertyResponse = await api.get(`/api/properties/${id}`);

        console.log("Property details:", propertyResponse.data);

        setProperty(propertyResponse.data);

        // Fetch rooms belonging to this property
        const roomsResponse = await api.get(`/api/rooms/property/${id}`);

        console.log("Property rooms:", roomsResponse.data);

        setRooms(roomsResponse.data);
      } catch (error) {
        console.error("Failed to fetch property details:", error);

        setError("Unable to load property details.");
      } finally {
        setLoading(false);
      }
    };

    fetchPropertyDetails();
  }, [id]);

  // Loading
  if (loading) {
    return (
      <div className="properties-page">
        <h2>Loading property...</h2>
      </div>
    );
  }

  // Error
  if (error) {
    return (
      <div className="properties-page">
        <h2>{error}</h2>
      </div>
    );
  }

  return (
    <div className="properties-page">
      {/* Property information */}

      <h1>{property.name}</h1>

      <p>{property.description}</p>

      <p>
        📍 {property.city}, {property.state}, {property.country}
      </p>

      <p>🏠 {property.address}</p>

      <p>
        🛏️ {property.bedrooms} Bedrooms
        {" · "}
        🚿 {property.bathrooms} Bathrooms
      </p>

      <p>👥 Maximum Guests: {property.maxGuests}</p>

      <p>💰 ₹{property.pricePerNight} / night</p>

      <p>✨ Amenities: {property.amenities}</p>

      <p>{property.available ? "✅ Available" : "❌ Currently unavailable"}</p>

      {/* Rooms */}

      <h2>Available Rooms</h2>

      {rooms.length === 0 ? (
        <p>No rooms available for this property.</p>
      ) : (
        <div className="properties-grid">
          {rooms.map((room) => (
            <div className="property-card" key={room.id}>
              <h3>Room {room.roomNumber}</h3>

              <p>Type: {room.roomType}</p>

              <p>Price: ₹{room.pricePerNight} / night</p>

              <p>Capacity: {room.capacity}</p>

              <p>{room.available ? "✅ Available" : "❌ Not Available"}</p>

              <button
                onClick={() =>
                  navigate("/booking", {
                    state: {
                      property: property,
                      room: room,
                    },
                  })
                }
              >
                Book Room
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default PropertyDetails;
