import { useEffect, useState } from "react";
import api from "../services/api";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

function MyBookings() {
  const { user } = useAuth();

  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const navigate = useNavigate();
  // Fetch user's bookings
  const fetchBookings = async () => {
    try {
      console.log("Logged-in user:", user);

      const response = await api.get(`/api/bookings/user/${user.userId}`);

      console.log("My bookings:", response.data);

      const bookingsWithDetails = await Promise.all(
        response.data.map(async (booking) => {
          try {
            const [propertyResponse, roomResponse, paymentResponse] =
              await Promise.all([
                api.get(`/api/properties/${booking.propertyId}`),
                api.get(`/api/rooms/${booking.roomId}`),
                api.get(`/api/payments/booking/${booking.id}`),
              ]);

            return {
              ...booking,
              property: propertyResponse.data,
              room: roomResponse.data,
              payment: paymentResponse.data,
            };
          } catch (error) {
            console.error(
              `Failed to fetch details for booking ${booking.id}:`,
              error,
            );

            return {
              ...booking,
              property: null,
              room: null,
              payment: null,
            };
          }
        }),
      );

      console.log("Bookings with details:", bookingsWithDetails);

      setBookings(bookingsWithDetails);
    } catch (error) {
      console.error("Failed to fetch bookings:", error);

      setError(error.response?.data?.message || "Unable to load bookings.");
    } finally {
      setLoading(false);
    }
  };
  useEffect(() => {
    if (user?.userId) {
      fetchBookings();
    } else {
      setLoading(false);
    }
  }, [user]);

  // Cancel booking
  const handleCancelBooking = async (bookingId) => {
    try {
      await api.delete(`/api/bookings/${bookingId}`);

      alert("Booking cancelled successfully!");

      // Refresh bookings after cancellation
      await fetchBookings();
    } catch (error) {
      console.error("Cancel booking failed:", error);

      alert(error.response?.data?.message || "Failed to cancel booking");
    }
  };

  if (!user?.userId) {
    return <h2>Please login to view your bookings.</h2>;
  }

  if (loading) {
    return <h2>Loading your bookings...</h2>;
  }

  if (error) {
    return <h2>{error}</h2>;
  }

  return (
    <div className="properties-page">
      <h1>My Bookings</h1>

      {bookings.length === 0 ? (
        <p>You don't have any bookings yet.</p>
      ) : (
        <div className="properties-grid">
          {bookings.map((booking) => (
            <div className="property-card" key={booking.id}>
              <h2>Booking #{booking.id}</h2>
              {booking.property && <p>🏨 Property: {booking.property.name}</p>}
              {booking.room && (
                <>
                  <p>🚪 Room: {booking.room.roomNumber}</p>

                  <p>🛏️ Room Type: {booking.room.roomType}</p>
                </>
              )}
              <p>Check-in: {booking.checkInDate}</p>
              <p>Check-out: {booking.checkOutDate}</p>
              <p>Guests: {booking.numberOfGuests}</p>
              <p>Total Price: ₹{booking.totalPrice}</p>
              <p>Status: {booking.bookingStatus}</p>
              {booking.payment && (
                <>
                  <p>Payment Status: {booking.payment.paymentStatus}</p>

                  <p>Payment Amount: ₹{booking.payment.amount}</p>
                </>
              )}
              {booking.bookingStatus !== "CANCELLED" && (
                <button onClick={() => handleCancelBooking(booking.id)}>
                  Cancel Booking
                </button>
              )}
              {booking.bookingStatus === "PENDING" &&
                booking.payment?.paymentStatus !== "SUCCESS" && (
                  <button
                    onClick={() =>
                      navigate("/payment", {
                        state: {
                          booking: booking,
                        },
                      })
                    }
                  >
                    Pay Now
                  </button>
                )}{" "}
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default MyBookings;
