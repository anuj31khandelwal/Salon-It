import React from 'react';
import { Scissors } from 'lucide-react';
import { useNavigate } from "react-router-dom";

const SalonPartnerPage = () => {
const navigate = useNavigate();

  const handleRegisterClick = () => {
    navigate("/add-salon-page");
  };
  return (
    <div className="salon-page">
      {/* Header Section */}
      <header className="salon-header">
        <h1>Partner with Salon-It</h1>
        <h2>at 0% commission for the 1st month!</h2>
        <p className="promotion">
          And get free promotion worth Rs. 1000. Valid for new stylist partners in select cities.
        </p>
        <div className="button-container">
          <a href="/add-salon-page" className="register-link">
            <button className="register-button">Register your salon</button>
          </a>
          <a href="/login" className="login-link">
            <button className="login-button">Login to view your bookings</button>
          </a>
        </div>
        <p className="contact-info">
          Need help? <a href="tel:+919414333018">Contact +91 9414333018</a>
        </p>
      </header>

      <section className="signup-section">
        <h3>Get started with online booking</h3>
        <p>Please keep the following ready for a smooth signup:</p>
        <ul className="document-list">
          {[
            'Cosmetology license copy',
            'ID proof',
            'Tax ID (if applicable)',
            'Bank account details',
            'Your service menu',
            'Photos of your best work',
          ].map((item, index) => (
            <li key={index} className="document-item">
              <Scissors className="scissors-icon" />
              <span>{item}</span>
            </li>
          ))}
        </ul>
      </section>
    </div>
  );
};

export default SalonPartnerPage;
