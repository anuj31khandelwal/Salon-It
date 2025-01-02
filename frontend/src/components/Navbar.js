import React from 'react';
import {Link} from 'react-router-dom';

function Navbar() {
  return (
    <nav className="navbar">
      <div className="navbar-container">
        <div className="navbar-left">
          <img src="/assets/logo.jpg" alt="Salon-It Logo" className="navbar-logo" />
        </div>
        <div className="navbar-right">
          <Link to="/investor-relations" className="navbar-link">Investor Relations</Link>
          <Link to="/salon-page" className="navbar-link">Add Salon</Link>
          <Link to="/login" className="navbar-link">Login</Link>
          <Link to="/signin" className="navbar-link">Sign In</Link>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;