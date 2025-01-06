import React from 'react';
import { useNavigate } from 'react-router-dom';

function SearchResultsPage({ salons }) {
  const navigate = useNavigate();

  const handleBookAppointment = (salonId) => {
    navigate(`/booking-form/${salonId}`);
  };

  return (
    <div className="search-results">
      {salons.map((salon) => (
        <div key={salon.id} className="salon-card">
          <h3>{salon.name}</h3>
          <p>{salon.location}</p>
          <ul>
            {salon.services.map((service) => (
              <li key={service.id}>
                {service.name} - ₹{service.price.toFixed(2)}
              </li>
            ))}
          </ul>
          {/* Add the "Book Appointment" button */}
          <button
            className="book-appointment-btn"
            onClick={() => handleBookAppointment(salon.id)}
          >
            Book Appointment
          </button>
        </div>
      ))}
    </div>
  );
}

export default SearchResultsPage;
