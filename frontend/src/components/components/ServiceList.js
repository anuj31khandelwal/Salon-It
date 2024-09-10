import React, { useState } from 'react';
import ServiceCard from './ServiceCard';

const services = [
  { id: 1, name: 'Beard Styling', imageUrl: 'assets/service1.jpg' },
  { id: 2, name: 'Facial', imageUrl: 'assets/service2.jpg' },
];

function ServiceList() {
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredServices, setFilteredServices] = useState(services);

  const handleSearch = (event) => {
    const term = event.target.value.toLowerCase();
    setSearchTerm(term);
    setFilteredServices(
      services.filter(service =>
        service.name.toLowerCase().includes(term)
      )
    );
  };

  return (
    <div>
      <input
        type="text"
        value={searchTerm}
        onChange={handleSearch}
        placeholder="Search for a service..."
      />
      <div>
        {filteredServices.map(service => (
          <ServiceCard key={service.id} service={service} />
        ))}
      </div>
    </div>
  );
}

export default ServiceList;