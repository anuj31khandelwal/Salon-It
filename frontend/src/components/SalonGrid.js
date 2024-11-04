import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const SalonGrid = () => {
    const [salonData, setSalonData] = useState([]);

    useEffect(() => {
        const fetchSalons = async () => {
            try {
                const response = await fetch('/superadmin/salons'); // Adjust the URL as necessary
                const data = await response.json();

                // Map and enrich the data to match the desired format
                const enrichedData = data.map(salon => ({
                    id: salon.id,
                    name: salon.name,
                    address: salon.location,
                    rating: (Math.random() * 1 + 4).toFixed(1), // Mock rating between 4.0 and 5.0
                    duration: Math.floor(Math.random() * 50) + 10, // Mock duration between 10 and 60 mins
                    image: "https://via.placeholder.com/200", // Placeholder image
                }));

                setSalonData(enrichedData);
            } catch (error) {
                console.error('Failed to fetch salons:', error);
            }
        };

        fetchSalons();
    }, []); // Run once on mount

const navigate = useNavigate();
    const handleSalonClick = (salon) => {
            navigate(`/salon/${salon.id}`, { state: { salon } });
        };


    const handleBackToServices = () => {
        console.log('Back to services clicked');
        navigate('/salonList');
    };

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
