import { useState } from "react";
import { useLocation } from "react-router-dom";
import api from "../services/api";

function Booking() {
  const location = useLocation();

  const { property, room } = location.state || {};

  const [checkInDate, setCheckInDate] = useState("");
  const [checkOutDate, setCheckOutDate] = useState("");
  const [numberOfGuests, setNumberOfGuests] = useState(1);

  const handleBooking = async (e) => {
    e.preventDefault();

    try {
      const request = {
        userId: 1,
        propertyId: property.id,
        roomId: room.id,
        checkInDate: checkInDate,
        checkOutDate: checkOutDate,
        numberOfGuests: Number(numberOfGuests),
      };

      console.log("Booking request:", request);

      const response = await api.post("/api/bookings", request);

      console.log("Booking successful:", response.data);

      alert("Booking created successfully!");
    } catch (error) {
      console.error("Booking failed:", error);

      alert(error.response?.data?.message || "Booking failed");
    }
  };

  if (!property || !room) {
    return <h2>Property or room information not found.</h2>;
  }

  return (
    <div className="booking-container">
      <h1>Book Your Room</h1>

      <h2>{property.name}</h2>

      <p>Room: {room.roomNumber}</p>

      <p>Price: ₹{room.pricePerNight} / night</p>

      <form onSubmit={handleBooking}>
        <label>Check-in Date</label>

        <input
          type="date"
          value={checkInDate}
          onChange={(e) => setCheckInDate(e.target.value)}
          required
        />

        <label>Check-out Date</label>

        <input
          type="date"
          value={checkOutDate}
          onChange={(e) => setCheckOutDate(e.target.value)}
          required
        />

        <label>Number of Guests</label>

        <input
          type="number"
          min="1"
          value={numberOfGuests}
          onChange={(e) => setNumberOfGuests(e.target.value)}
          required
        />

        <button type="submit">Confirm Booking</button>
      </form>
    </div>
  );
}

export default Booking;
