// import React, { useState } from 'react';
// import ServiceCard from './ServiceCard';

// const services = [
//   { id: 1, name: 'Beard Styling', imageUrl: 'assets/service1.jpg' },
//   { id: 2, name: 'Facial', imageUrl: 'assets/service2.jpg' },
// ];

// function ServiceList() {
//   const [searchTerm, setSearchTerm] = useState('');
//   const [filteredServices, setFilteredServices] = useState(services);

//   const handleSearch = (event) => {
//     const term = event.target.value.toLowerCase();
//     setSearchTerm(term);
//     setFilteredServices(
//       services.filter(service =>
//         service.name.toLowerCase().includes(term)
//       )
//     );
//   };

//   return (
//     <div>
//       <input
//         type="text"
//         value={searchTerm}
//         onChange={handleSearch}
//         placeholder="Search for a service..."
//       />
//       <div>
//         {filteredServices.map(service => (
//           <ServiceCard key={service.id} service={service} />
//         ))}
//       </div>
//     </div>
//   );
// }

// export default ServiceList;
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
    <div className="service-list">
      {services.map((service, index) => (
        <div key={index} className="service-card">
          <img src={service.image} alt={service.title} className="service-image" />
          <h3 className="service-title">{service.title}</h3>
          <p className="service-description">{service.description}</p>
        </div>
      ))}
    </div>
  );
};

export default ServiceList;