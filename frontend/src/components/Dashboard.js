import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { User, LogOut, Home, Settings } from 'lucide-react';
import styles from './Dashboard.module.css';

const Dashboard = () => {
  const [userData, setUserData] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const storedUserData = localStorage.getItem('userData');
    if (storedUserData) {
      const parsedData = JSON.parse(storedUserData);
      setUserData(parsedData);
    } else {
      navigate('/login');
    }
  }, [navigate]);

  const handleLogout = () => {
    // Clear user data and redirect to login
    localStorage.removeItem('userData');
    navigate('/login');
  };

  if (!userData) return null;

  return (
    <div className={styles['dashboard-container']}>
      {/* Personalized Navbar */}
      <nav className={styles.navbar}>
        <div className="flex items-center space-x-4">
          <User className="h-6 w-6" />
          <span className="text-lg font-semibold">
            Welcome, {userData}
          </span>
        </div>
        <div className="flex space-x-4">
          <button
            onClick={() => navigate('/home')}
            className={styles['dashboard-button']}
          >
            <Home className="h-5 w-5" />
            <span>Home</span>
          </button>
          <button
            onClick={() => navigate('/profile')}
            className={styles['dashboard-button']}
          >
            <Settings className="h-5 w-5" />
            <span>Profile</span>
          </button>
          <button
            onClick={handleLogout}
            className={styles['dashboard-button']}
          >
            <LogOut className="h-5 w-5" />
            <span>Logout</span>
          </button>
        </div>
      </nav>

      {/* Dashboard Content */}
      <div className={styles['dashboard-content']}>
        <h1 className="text-2xl font-bold mb-4">Dashboard</h1>
        <div className={styles['dashboard-grid']}>
          {userData.userType === 'customer' && (
            <div className={styles['dashboard-card']}>
              <h2 className="text-xl font-semibold mb-2">Customer Overview</h2>
              <p>View your booking history, upcoming appointments, and more.</p>
            </div>
          )}
          {userData.userType === 'salonOwner' && (
            <div className={styles['dashboard-card']}>
              <h2 className="text-xl font-semibold mb-2">Salon Management</h2>
              <p>Manage your salon, view bookings, and track performance.</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
