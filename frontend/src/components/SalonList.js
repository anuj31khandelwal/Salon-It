import React from 'react';
import {Link} from 'react-router-dom';

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
          <Link key={index} to="/salongrid" className="salon-card-link">
                      <div className="salon-card">
                        <img src={salon.image} alt={salon.title} className="salon-image" />
                        <div className="salon-info">
                          <h3 className="salon-title">{salon.title}</h3>
                          <p className="salon-places">{salon.places} Places ›</p>
                        </div>
                      </div>
                    </Link>
        ))}
      </div>
      <Link to="/salongrid" className="see-all-link">All collections in your city ›</Link>
    </div>
  );
};

export default SalonList;