import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../assets/logo.png'; // Assuming you have a logo

function Header() {
  return (
    <header>
      <img src={logo} alt="Logo" />
      <nav>
        <Link to="/">Home</Link>
        <Link to="/about">About Us</Link>
        <Link to="/contact">Contact</Link>
      </nav>
    </header>
  );
}

export default Header;
