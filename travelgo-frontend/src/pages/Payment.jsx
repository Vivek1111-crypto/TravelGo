import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import api from "../services/api";

function Payment() {
  const location = useLocation();
  const navigate = useNavigate();

  const { booking } = location.state || {};

  const [paymentMethod, setPaymentMethod] = useState("CARD");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handlePayment = async (e) => {
    e.preventDefault();

    if (!booking) {
      return;
    }

    try {
      setLoading(true);
      setError("");

      const paymentRequest = {
        bookingId: booking.id,
        amount: booking.totalPrice,
        paymentMethod: paymentMethod,
      };

      console.log("Payment request:", paymentRequest);

      const response = await api.post("/api/payments", paymentRequest);

      console.log("Payment successful:", response.data);

      alert("Payment successful!");

      // Go back to My Bookings
      navigate("/bookings");
    } catch (error) {
      console.error("Payment failed:", error);

      setError(error.response?.data?.message || "Payment failed");
    } finally {
      setLoading(false);
    }
  };

  if (!booking) {
    return (
      <div className="booking-container">
        <h2>Booking information not found.</h2>

        <button onClick={() => navigate("/bookings")}>Go to My Bookings</button>
      </div>
    );
  }

  return (
    <div className="booking-container">
      <h1>Make Payment</h1>

      <h2>Booking #{booking.id}</h2>

      <p>Property ID: {booking.propertyId}</p>

      <p>Room ID: {booking.roomId}</p>

      <p>Check-in: {booking.checkInDate}</p>

      <p>Check-out: {booking.checkOutDate}</p>

      <p>Guests: {booking.numberOfGuests}</p>

      <h2>Amount: ₹{booking.totalPrice}</h2>

      <form onSubmit={handlePayment}>
        <label>Payment Method</label>

        <select
          value={paymentMethod}
          onChange={(e) => setPaymentMethod(e.target.value)}
        >
          <option value="CARD">Card</option>
          <option value="UPI">UPI</option>
          <option value="NET_BANKING">Net Banking</option>
        </select>

        <br />
        <br />

        {error && <p style={{ color: "red" }}>{error}</p>}

        <button type="submit" disabled={loading}>
          {loading ? "Processing..." : "Pay Now"}
        </button>
      </form>
    </div>
  );
}

export default Payment;
