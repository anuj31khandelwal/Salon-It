import React from 'react';
import SalonCard from './SalonCard';

const salons = [
  { id: 1, name: 'Salon A', rating: 4.5, imageUrl: 'assets/salon1.jpeg' },
  { id: 2, name: 'Salon B', rating: 4.2, imageUrl: 'assets/salon2.jpg' },
];

const SalonList = () => {
  return (
    <div className="salon-list">
      {salons.map(salon => (
        <SalonCard key={salon.id} salon={salon} />
      ))}
    </div>
  );
};

export default SalonList;
