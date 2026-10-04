import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function ManageRooms() {
  const navigate = useNavigate();

  const [rooms, setRooms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchRooms = async () => {
    try {
      setLoading(true);
      setError("");

      const response = await api.get("/api/rooms");

      console.log("Admin rooms:", response.data);

      setRooms(response.data);
    } catch (error) {
      console.error("Failed to fetch rooms:", error);

      setError(error.response?.data?.message || "Failed to load rooms");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchRooms();
  }, []);

  if (loading) {
    return (
      <div className="properties-page">
        <h2>Loading rooms...</h2>
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
      "Are you sure you want to delete this room?",
    );

    if (!confirmed) {
      return;
    }

    try {
      await api.delete(`/api/rooms/${id}`);

      alert("Room deleted successfully!");

      fetchRooms();
    } catch (error) {
      console.error("Failed to delete room:", error);

      alert(error.response?.data?.message || "Failed to delete room");
    }
  };
  return (
    <div className="properties-page">
      <h1>Manage Rooms</h1>

      <button onClick={() => navigate("/admin/rooms/add")}>+ Add Room</button>

      <div className="properties-grid">
        {rooms.map((room) => (
          <div className="property-card" key={room.id}>
            <h2>Room {room.roomNumber}</h2>

            <p>🏨 Property: {room.propertyName}</p>

            <p>🛏️ Type: {room.roomType}</p>

            <p>💰 ₹{room.pricePerNight} / night</p>

            <p>👥 Capacity: {room.capacity}</p>

            <p>{room.available ? "✅ Available" : "❌ Unavailable"}</p>

            <button onClick={() => navigate(`/admin/rooms/edit/${room.id}`)}>
              Edit
            </button>

            <button onClick={() => handleDelete(room.id)}>Delete</button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ManageRooms;
