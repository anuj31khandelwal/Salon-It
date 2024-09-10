import React from 'react';

const SalonCard = ({ salon }) => {
  return (
    <div className="salon-card">
      <img src={salon.imageUrl} alt={salon.name} className="salon-image" />
      <h3>{salon.name}</h3>
      <p>Rating: {salon.rating}</p>
    </div>
  );
};

export default SalonCard;
