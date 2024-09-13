import React from 'react';

const ServiceCard = ({ services }) => {
  return (
    <div className="cards">
     {services.length > 0 ? (
        services.map((service, index) => (
          <div className="card" key={index}>
            <img src={service.imageUrl} alt={service.name} className="service-image" />
            <h3>{service.name}</h3>
          </div>
        ))
      ) : (
        <p>No services available</p>  // Optional: You can display this message if there are no services
      )}
    </div>
  );
};

export default ServiceCard;
