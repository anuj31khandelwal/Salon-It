import React from 'react';
import { Scissors } from 'lucide-react';

const SalonPartnerPage = () => {
  return (
    <div className="salon-page">
      <header className="salon-header">
        <h1>Partner with BeautySalon</h1>
        <h2>at 0% commission for the 1st month!</h2>
        <p className="promotion">And get free promotion worth Rs1000. Valid for new stylist partners in select cities.</p>
        <div className="button-container">
          <a href="/signin">
          <button className="register-button">Register your salon</button>
          </a>
          <button className="login-button">Login to view your bookings</button>
        </div>
        <p className="contact-info">Need help? Contact +91 9414333018</p>
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
            'Photos of your best work'
          ].map((item, index) => (
            <li key={index}>
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