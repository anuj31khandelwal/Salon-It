import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

function Navbar() {
  const [isLoggedIn, setIsLoggedIn] = useState(false); // State to track login status
  const navigate = useNavigate();

  // Check login status on component mount and when localStorage changes
  useEffect(() => {
    const checkLoginStatus = () => {
      const userData = localStorage.getItem('userData'); // Assuming login data is stored in localStorage
      setIsLoggedIn(!!userData); // Convert to boolean
    };

    checkLoginStatus();

    // Add an event listener for storage updates
    window.addEventListener('storage', checkLoginStatus);

    // Cleanup the event listener
    return () => {
      window.removeEventListener('storage', checkLoginStatus);
    };
  }, []);

  // Logout handler
  const handleLogout = () => {
    localStorage.removeItem('userData'); // Clear user data
    setIsLoggedIn(false);
    navigate('/'); // Redirect to home page after logout
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <div className="navbar-left">
          <img src="/assets/logo.jpg" alt="Salon-It Logo" className="navbar-logo" />
        </div>
        <div className="navbar-right">
          <Link to="/investor-relations" className="navbar-link">Investor Relations</Link>
          <Link to="/salon-page" className="navbar-link">Add Salon</Link>
          {isLoggedIn ? (
            <>
              <Link to="/orders" className="navbar-link">My Orders</Link>
              <Link to="/profile" className="navbar-link">Profile</Link>
              <button onClick={handleLogout} className="navbar-link">Logout</button>
            </>
          ) : (
            <>
              <Link to="/login" className="navbar-link">Login</Link>
              <Link to="/signin" className="navbar-link">Sign In</Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
