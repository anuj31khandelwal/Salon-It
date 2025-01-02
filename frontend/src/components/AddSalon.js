import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const AddSalon = () => {
  const navigate = useNavigate(); // Use the useNavigate hook to redirect
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [services, setServices] = useState("");
  const [street, setStreet] = useState("");
  const [state, setState] = useState("");
  const [city, setCity] = useState("");
  const [pincode, setPincode] = useState("");

  const stateCityMap = {
    "Andhra Pradesh": ["Vishakhapatnam", "Vijayawada", "Guntur"],
    "Arunachal Pradesh": ["Itanagar", "Pasighat", "Tawang"],
    "Assam": ["Guwahati", "Dibrugarh", "Tezpur"],
    "Bihar": ["Patna", "Gaya", "Bhagalpur"],
    "Madhya Pradesh": ["Bhopal", "Indore", "Gwalior"],
    "Maharashtra": ["Mumbai", "Pune", "Nagpur"],
    // Add more states and cities as needed
  };

  const states = Object.keys(stateCityMap);
  const cities = state ? stateCityMap[state] : [];

  const handleSubmit = async (e) => {
    e.preventDefault();

    const location = `${street}, ${city}, ${state}, ${pincode}`;

    const salonData = {
      name,
      location,
      services: services.split(",").map((serviceName) => ({ name: serviceName.trim() })),
    };

    console.log("Prepared Payload:", salonData);

    try {
      console.log("Sending API Request...");
      const response = await fetch("http://localhost:8080/salon/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(salonData),
      });

      console.log("Raw Response Status:", response.status);
      console.log("Raw Response Headers:", response.headers);

      if (!response.ok) {
        const errorData = await response.json();
        console.error("API Error Response:", errorData);
      } else {
        const successData = await response.json();
        console.log("API Success Response:", successData);

        // After salon is successfully registered, redirect to document upload page
        const salonId = successData.id; // Assuming you get the salon ID from the response
        navigate(`/upload-documents/${salonId}`); // Redirect using useNavigate hook
      }
    } catch (error) {
      console.error("Error during API request:", error);
    }
  };

  return (
    <div className="add-salon-page">
      <h1>Add Salon</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Name</label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="Enter salon name"
            required
          />
        </div>
        <div>
          <label>Description</label>
          <input
            type="text"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="Enter salon description"
            required
          />
        </div>
        <div>
          <label>Services (comma-separated)</label>
          <input
            type="text"
            value={services}
            onChange={(e) => setServices(e.target.value)}
            placeholder="Enter services (e.g., haircut, beard trim)"
            required
          />
        </div>
        <div>
          <label>Street Address</label>
          <input
            type="text"
            value={street}
            onChange={(e) => setStreet(e.target.value)}
            placeholder="Enter street address"
            required
          />
        </div>
        <div>
          <label>State</label>
          <select
            value={state}
            onChange={(e) => {
              setState(e.target.value);
              setCity(""); // Reset city when state changes
            }}
            required
          >
            <option value="" disabled>
              Select a state
            </option>
            {states.map((state) => (
              <option key={state} value={state}>
                {state}
              </option>
            ))}
          </select>
        </div>
        <div>
          <label>City</label>
          <select
            value={city}
            onChange={(e) => setCity(e.target.value)}
            required
            disabled={!cities.length}
          >
            <option value="" disabled>
              {cities.length ? "Select a city" : "Select a state first"}
            </option>
            {cities.map((city) => (
              <option key={city} value={city}>
                {city}
              </option>
            ))}
          </select>
        </div>
        <div>
          <label>Pincode</label>
          <input
            type="text"
            value={pincode}
            onChange={(e) => setPincode(e.target.value)}
            placeholder="Enter pincode"
            required
          />
        </div>
        <button className="add-salon-button" type="submit">
          Register Salon
        </button>
      </form>
    </div>
  );
};

export default AddSalon;
