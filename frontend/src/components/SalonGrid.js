import React from "react";
import { useNavigate } from 'react-router-dom';

const SalonGrid = () => {
  const navigate = useNavigate();

  const handleBackToServices = () => {
    navigate('/'); // Change this to the appropriate route for services
  };

  const handleSalonClick = (salon) => {
    // Navigate to the booking page for the selected salon
    navigate(`/customer/book/${salon.id}`);
  };

  const salonData = [
    {
      id: 1,
      name: "Glamour Studio",
      address: "123 Main St",
      rating: 4.5,
      duration: 25,
      image: "https://via.placeholder.com/200",
    },
    {
      id: 2,
      name: "Chic Salon",
      address: "456 Elm St",
      rating: 4.2,
      duration: 20,
      image: "https://via.placeholder.com/200",
    },
    {
      id: 3,
      name: "Style Hub",
      address: "789 Pine St",
      rating: 4.3,
      duration: 30,
      image: "https://via.placeholder.com/200",
    },
    {
      id: 4,
      name: "Urban Edge Salon",
      address: "101 Oak Ave",
      rating: 4.7,
      duration: 35,
      image: "https://via.placeholder.com/200",
    },
    {
      id: 5,
      name: "Lavish Locks",
      address: "202 Birch Rd",
      rating: 4.6,
      duration: 40,
      image: "https://via.placeholder.com/200",
    },
    {
      id: 6,
      name: "Modern Makeover",
      address: "303 Cedar St",
      rating: 4.4,
      duration: 15,
      image: "https://via.placeholder.com/200",
    },
    {
      id: 7,
      name: "Elite Hair & Spa",
      address: "404 Willow Ln",
      rating: 4.8,
      duration: 50,
      image: "https://via.placeholder.com/200",
    },
    {
      id: 8,
      name: "Luxury Hair Lounge",
      address: "505 Maple Dr",
      rating: 4.1,
      duration: 45,
      image: "https://via.placeholder.com/200",
    },
    {
      id: 9,
      name: "Royal Salon",
      address: "606 Walnut Blvd",
      rating: 4.9,
      duration: 60,
      image: "https://via.placeholder.com/200",
    },
    {
      id: 10,
      name: "Beauty Bliss",
      address: "707 Chestnut St",
      rating: 4.0,
      duration: 10,
      image: "https://via.placeholder.com/200",
    },
  ];

  return (
    <div className="salon-grid-container">
      <h1>Available Salons</h1>
      <div className="salon-grid">
        {salonData.map((salon) => (
          <div key={salon.id} className="salon-card" onClick={() => handleSalonClick(salon)}>
            <img src={salon.image} alt={salon.name} className="salon-image" />
            <div className="salon-info">
              <h3>{salon.name}</h3>
              <p>{salon.address}</p>
              <p className="salon-rating">{salon.rating} ★</p>
              <p>{salon.duration} min</p>
            </div>
          </div>
        ))}
      </div>
      <button className="back-button" onClick={handleBackToServices}>
        Back to Services
      </button>
    </div>
  );
};

export default SalonGrid;
