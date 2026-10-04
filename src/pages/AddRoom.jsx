import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function AddRoom() {
  const navigate = useNavigate();

  const [properties, setProperties] = useState([]);

  const [formData, setFormData] = useState({
    roomNumber: "",
    roomType: "STANDARD",
    pricePerNight: "",
    capacity: "",
    available: true,
    propertyId: "",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchProperties = async () => {
      try {
        const response = await api.get("/api/properties");
        setProperties(response.data);
      } catch (error) {
        console.error("Failed to fetch properties:", error);
        setError("Unable to load properties.");
      }
    };

    fetchProperties();
  }, []);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;

    setFormData({
      ...formData,
      [name]: type === "checkbox" ? checked : value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      setLoading(true);
      setError("");

      const request = {
        roomNumber: formData.roomNumber,
        roomType: formData.roomType,
        pricePerNight: Number(formData.pricePerNight),
        capacity: Number(formData.capacity),
        available: formData.available,
        propertyId: Number(formData.propertyId),
      };

      console.log("Add room request:", request);

      const response = await api.post("/api/rooms", request);

      console.log("Room created:", response.data);

      alert("Room added successfully!");

      navigate("/admin");
    } catch (error) {
      console.error("Failed to add room:", error);

      setError(error.response?.data?.message || "Failed to add room");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="booking-container">
      <h1>Add Room</h1>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <form onSubmit={handleSubmit}>
        <label>Room Number</label>
        <input
          name="roomNumber"
          value={formData.roomNumber}
          onChange={handleChange}
          placeholder="101"
          required
        />

        <label>Room Type</label>
        <select
          name="roomType"
          value={formData.roomType}
          onChange={handleChange}
          required
        >
          <option value="STANDARD">Standard</option>
          <option value="DELUXE">Deluxe</option>
          <option value="SUITE">Suite</option>
          <option value="EXECUTIVE">Executive</option>
        </select>

        <label>Price Per Night</label>
        <input
          type="number"
          name="pricePerNight"
          value={formData.pricePerNight}
          onChange={handleChange}
          required
        />

        <label>Capacity</label>
        <input
          type="number"
          name="capacity"
          value={formData.capacity}
          onChange={handleChange}
          min="1"
          required
        />

        <label>Property</label>
        <select
          name="propertyId"
          value={formData.propertyId}
          onChange={handleChange}
          required
        >
          <option value="">Select Property</option>

          {properties.map((property) => (
            <option key={property.id} value={property.id}>
              {property.name}
            </option>
          ))}
        </select>

        <label>
          <input
            type="checkbox"
            name="available"
            checked={formData.available}
            onChange={handleChange}
          />
          Available
        </label>

        <button type="submit" disabled={loading}>
          {loading ? "Adding..." : "Add Room"}
        </button>
      </form>
    </div>
  );
}

export default AddRoom;
