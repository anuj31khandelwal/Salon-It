import React from 'react';

const Navbar = () => {
  return (
    <nav className="navbar">
      <div className="navbar-logo">
        <img src="/assets/logo.jpg" alt="Salon-It!" className="logo-image" />
      </div>
      <div className="navbar-links">
        <a href="/Home">Home</a>
        <a href="About">About</a>
        <a href="Invest">Invester</a>
        <a href="/login">Log in</a>
        <a href="/signup">Sign up</a>
      </div>
    </nav>
  );
};

export default Navbar;
