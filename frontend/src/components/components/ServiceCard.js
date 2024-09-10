import React from 'react';

const ServiceCard = ({ service }) => {
  return (
    <div className="service-card">
      <img src={service.imageUrl} alt={service.name} className="service-image" />
      <h3>{service.name}</h3>
    </div>
  );
};

export default ServiceCard;
