import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../services/api";

function EditRoom() {
  const { id } = useParams();
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

  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [roomResponse, propertiesResponse] = await Promise.all([
          api.get(`/api/rooms/${id}`),
          api.get("/api/properties"),
        ]);

        const room = roomResponse.data;

        setFormData({
          roomNumber: room.roomNumber,
          roomType: room.roomType,
          pricePerNight: room.pricePerNight,
          capacity: room.capacity,
          available: room.available,
          propertyId: room.propertyId,
        });

        setProperties(propertiesResponse.data);
      } catch (error) {
        console.error("Failed to load room:", error);

        setError(error.response?.data?.message || "Failed to load room");
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

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
      setSaving(true);
      setError("");

      const request = {
        roomNumber: formData.roomNumber,
        roomType: formData.roomType,
        pricePerNight: Number(formData.pricePerNight),
        capacity: Number(formData.capacity),
        available: formData.available,
        propertyId: Number(formData.propertyId),
      };

      console.log("Update room request:", request);

      await api.put(`/api/rooms/${id}`, request);

      alert("Room updated successfully!");

      navigate("/admin/rooms");
    } catch (error) {
      console.error("Failed to update room:", error);

      setError(error.response?.data?.message || "Failed to update room");
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return (
      <div className="booking-container">
        <h2>Loading room...</h2>
      </div>
    );
  }

  return (
    <div className="booking-container">
      <h1>Edit Room</h1>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <form onSubmit={handleSubmit}>
        <label>Room Number</label>
        <input
          name="roomNumber"
          value={formData.roomNumber}
          onChange={handleChange}
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

        <button type="submit" disabled={saving}>
          {saving ? "Updating..." : "Update Room"}
        </button>
      </form>
    </div>
  );
}

export default EditRoom;
