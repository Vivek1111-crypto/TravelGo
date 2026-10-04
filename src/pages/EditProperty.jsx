import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../services/api";

function EditProperty() {
  const { id } = useParams();
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
  });

  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchProperty = async () => {
      try {
        const response = await api.get(`/api/properties/${id}`);

        setFormData({
          name: response.data.name,
          description: response.data.description,
          city: response.data.city,
          state: response.data.state,
          country: response.data.country,
          address: response.data.address,
          pricePerNight: response.data.pricePerNight,
          maxGuests: response.data.maxGuests,
          bedrooms: response.data.bedrooms,
          bathrooms: response.data.bathrooms,
          amenities: response.data.amenities || "",
        });
      } catch (error) {
        console.error("Failed to fetch property:", error);

        setError(error.response?.data?.message || "Failed to load property");
      } finally {
        setLoading(false);
      }
    };

    fetchProperty();
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      setSaving(true);
      setError("");

      const request = {
        ...formData,
        pricePerNight: Number(formData.pricePerNight),
        maxGuests: Number(formData.maxGuests),
        bedrooms: Number(formData.bedrooms),
        bathrooms: Number(formData.bathrooms),
      };

      console.log("Update property request:", request);

      await api.put(`/api/properties/${id}`, request);

      alert("Property updated successfully!");

      navigate("/admin/properties");
    } catch (error) {
      console.error("Failed to update property:", error);

      setError(error.response?.data?.message || "Failed to update property");
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return (
      <div className="booking-container">
        <h2>Loading property...</h2>
      </div>
    );
  }

  return (
    <div className="booking-container">
      <h1>Edit Property</h1>

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

        <button type="submit" disabled={saving}>
          {saving ? "Updating..." : "Update Property"}
        </button>
      </form>
    </div>
  );
}

export default EditProperty;
