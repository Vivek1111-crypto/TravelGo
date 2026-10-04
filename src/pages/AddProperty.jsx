import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function AddProperty() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    description: "",
    city: "",
    state: "",
    country: "",
    address: "",
    pricePerNight: "",
    maxGuests: "",
    bedrooms: "",
    bathrooms: "",
    amenities: "",
    available: true,
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

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
        ...formData,
        pricePerNight: Number(formData.pricePerNight),
        maxGuests: Number(formData.maxGuests),
        bedrooms: Number(formData.bedrooms),
        bathrooms: Number(formData.bathrooms),
      };

      console.log("Add property request:", request);

      const response = await api.post("/api/properties", request);

      console.log("Property created:", response.data);

      alert("Property added successfully!");

      navigate("/admin");
    } catch (error) {
      console.error("Failed to add property:", error);

      setError(error.response?.data?.message || "Failed to add property");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="booking-container">
      <h1>Add Property</h1>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <form onSubmit={handleSubmit}>
        <label>Property Name</label>
        <input
          name="name"
          value={formData.name}
          onChange={handleChange}
          required
        />

        <label>Description</label>
        <input
          name="description"
          value={formData.description}
          onChange={handleChange}
          required
        />

        <label>City</label>
        <input
          name="city"
          value={formData.city}
          onChange={handleChange}
          required
        />

        <label>State</label>
        <input
          name="state"
          value={formData.state}
          onChange={handleChange}
          required
        />

        <label>Country</label>
        <input
          name="country"
          value={formData.country}
          onChange={handleChange}
          required
        />

        <label>Address</label>
        <input
          name="address"
          value={formData.address}
          onChange={handleChange}
          required
        />

        <label>Price Per Night</label>
        <input
          type="number"
          name="pricePerNight"
          value={formData.pricePerNight}
          onChange={handleChange}
          required
        />

        <label>Maximum Guests</label>
        <input
          type="number"
          name="maxGuests"
          value={formData.maxGuests}
          onChange={handleChange}
          required
        />

        <label>Bedrooms</label>
        <input
          type="number"
          name="bedrooms"
          value={formData.bedrooms}
          onChange={handleChange}
          required
        />

        <label>Bathrooms</label>
        <input
          type="number"
          name="bathrooms"
          value={formData.bathrooms}
          onChange={handleChange}
          required
        />

        <label>Amenities</label>
        <input
          name="amenities"
          value={formData.amenities}
          onChange={handleChange}
          placeholder="WiFi, Pool, Parking"
        />

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
          {loading ? "Adding..." : "Add Property"}
        </button>
      </form>
    </div>
  );
}

export default AddProperty;
