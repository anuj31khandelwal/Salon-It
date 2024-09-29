import React from 'react';

const services = [
  {
    title: "Haircuts & Styling",
    description: "Professional cuts and styles for all hair types",
    image: "/assets/haircut.jpeg"
  },
  {
    title: "Color & Highlights",
    description: "Vibrant colors and dimensional highlights",
    image: "/assets/haircolor.jpeg"
  },
  {
    title: "Spa & Relaxation",
    description: "Rejuvenating treatments for ultimate relaxation",
    image: "/assets/facial.jpeg"
  }
];

const ServiceList = () => {
  return (
    <div className="service-section">
      <h2 className="service-section-title">Services Available</h2>
      <p className="service-section-description">
        Explore various services offered by salons
      </p>
    <div className="service-list">
      {services.map((service, index) => (
        <div key={index} className="service-card">
          <img src={service.image} alt={service.title} className="service-image" />
          <h3 className="service-title">{service.title}</h3>
          <p className="service-description">{service.description}</p>
        </div>
      ))}
    </div>
    </div>
  );
};

export default ServiceList;