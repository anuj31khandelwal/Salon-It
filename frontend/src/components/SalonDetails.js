import React, { useState } from 'react';
import BookAppointment from './BookAppointment';
import { Star, MapPin, Phone, Clock, Coffee, Scissors, SprayCan } from 'lucide-react';

const SalonDetailsPage = ({ salon }) => {
  const [activeTab, setActiveTab] = useState('overview');

  // If salon is undefined, show loading message
    if (!salon) {
      return <p>Loading salon details...</p>;
    }

  const renderTabContent = () => {
    switch (activeTab) {
      case 'overview':
        return (
          <div className="overview-content">
            <h3>About this place</h3>
            <div className="menu-preview">
              <h4>Services Menu</h4>
              <img src="/assets/menu.jpg" alt="Menu preview" />
              <button className="view-menu-btn">View full menu</button>
            </div>
            <div className="services-offered">
              <h4>Services Offered</h4>
              <div className="services-tags">
                <span><Coffee size={16} /> Hair Styling</span>
                <span><Scissors size={16} /> Haircuts</span>
                <span><SprayCan size={16} /> Coloring</span>
              </div>
            </div>
            <div className="additional-info">
              <h4>More Info</h4>
              <ul>
                <li>Accepts digital payments</li>
                <li>Unisex salon</li>
                <li>Parking available</li>
              </ul>
            </div>
          </div>
        );
      case 'book':
        return (
          <div className="book-content">
            <h3>Book an Appointment</h3>
            <BookAppointment/>
          </div>
        );
      case 'reviews':
        return (
          <div className="reviews-content">
            <h3>Customer Reviews</h3>
            {/* Add customer reviews here */}
          </div>
        );
      case 'photos':
        return (
          <div className="photos-content">
            <h3>Salon Photos</h3>
            <div className="photo-grid">
              <img src="/assets/salon1.jpeg" alt="Salon interior" />
              <img src="/assets/salon1.jpeg" alt="Salon exterior" />
              <img src="/assets/salon1.jpeg" alt="Styling area" />
              <img src="/assets/salon1.jpeg" alt="Waiting area" />
            </div>
          </div>
        );
      default:
        return null;
    }
  };

  return (
    <div className="salon-details-page">
      <div className="salon-header">
        <div className="salon-images">
          <img src="/assets/salon1.jpeg" alt={salon.name} className="main-image" />
          <div className="image-grid">
            <img src="/assets/salon1.jpeg" alt="Salon interior" />
            <img src="/assets/salon1.jpeg" alt="Salon exterior" />
            <img src="/assets/salon1.jpeg" alt="Styling area" />
            <button className="view-gallery-btn">View Gallery</button>
          </div>
        </div>
        <div className="salon-info">
          <h1>{salon.name}</h1>
          <p className="salon-description">{salon.description}</p>
          <div className="salon-meta">
            <span className="rating">
              <Star size={16} fill="#4CAF50" color="#4CAF50" />
              {salon.rating}
            </span>
            <span className="address">
              <MapPin size={16} /> {salon.address}
            </span>
            <span className="phone">
              <Phone size={16} /> {salon.phone}
            </span>
            <span className="hours">
              <Clock size={16} /> {salon.hours}
            </span>
          </div>
        </div>
      </div>
      <div className="salon-tabs">
        <button
          className={activeTab === 'overview' ? 'active' : ''}
          onClick={() => setActiveTab('overview')}
        >
          Overview
        </button>
        <button
          className={activeTab === 'book' ? 'active' : ''}
          onClick={() => setActiveTab('book')}
        >
          Book Appointment
        </button>
        <button
          className={activeTab === 'reviews' ? 'active' : ''}
          onClick={() => setActiveTab('reviews')}
        >
          Reviews
        </button>
        <button
          className={activeTab === 'photos' ? 'active' : ''}
          onClick={() => setActiveTab('photos')}
        >
          Photos
        </button>
      </div>
      <div className="tab-content">
        {renderTabContent()}
      </div>
      <div className="booking-cta">
        <button className="book-now-btn">Book Now</button>
        <p>Flat 10% OFF + 2 more offers available</p>
      </div>
    </div>
  );
};

export default SalonDetailsPage;