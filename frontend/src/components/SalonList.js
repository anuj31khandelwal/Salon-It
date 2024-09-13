// import React from 'react';
// import SalonCard from './SalonCard';

// const salons = [
//   { id: 1, name: 'Salon A', rating: 4.5, imageUrl: 'assets/salon1.jpeg' },
//   { id: 2, name: 'Salon B', rating: 4.2, imageUrl: 'assets/salon2.jpg' },
// ];

// const SalonList = () => {
//   return (
//     <div className="salon-list">
//       {salons.map(salon => (
//         <SalonCard key={salon.id} salon={salon} />
//       ))}
//     </div>
//   );
// };

// export default SalonList;
import React from 'react';

const salons = [
  {
    title: "9 Top-Rated Salons",
    places: 9,
    image: "/assets/salon1.jpeg"
  },
  {
    title: "16 Luxury Beauty Spas",
    places: 16,
    image: "/assets/salon2.jpg"
  },
  {
    title: "10 Best Nail Studios",
    places: 10,
    image: "/assets/salon1.jpeg"
  },
  {
    title: "12 Hair Color Specialists",
    places: 12,
    image: "/assets/salon2.jpg"
  }
];

const SalonList = () => {
  return (
    <div className="salon-section">
      <h2 className="salon-section-title">Collections</h2>
      <p className="salon-section-description">
        Explore curated lists of top salons, spas, and beauty studios in your city, based on trends
      </p>
      <div className="salon-list">
        {salons.map((salon, index) => (
          <div key={index} className="salon-card">
            <img src={salon.image} alt={salon.title} className="salon-image" />
            <div className="salon-info">
              <h3 className="salon-title">{salon.title}</h3>
              <p className="salon-places">{salon.places} Places ›</p>
            </div>
          </div>
        ))}
      </div>
      <a href="#" className="see-all-link">All collections in your city ›</a>
    </div>
  );
};

export default SalonList;