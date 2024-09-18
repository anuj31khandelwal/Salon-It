import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import SearchBar from './components/SearchBar';
import SalonList from './components/SalonList';
import ServiceList from './components/ServiceList';
import MobileView from './components/MobileView';
import Recommendations from './components/Recommendations';
import Footer from './components/Footer';
import { searchSalons } from './api';  // New import for API function
import './App.css';

function App() {
  const [searchQuery, setSearchQuery] = useState('');
  const [location, setLocation] = useState('');
  const [salons, setSalons] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSearch = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setError(null);

    try {
      const result = await searchSalons(location, searchQuery);
      setSalons(result);
    } catch (error) {
      console.error('Error fetching data:', error);
      setError('An error occurred while searching for salons. Please try again.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="app">
      <Navbar />
      <main>
        <section className="hero">
          <div className="hero-content">
            <h1>Salon-It</h1>
            <h2>Find the perfect salon</h2>
            <form onSubmit={handleSearch} className="search-form">
              <div className="location-input">
                <input
                  type="text"
                  placeholder="Location"
                  value={location}
                  onChange={(e) => setLocation(e.target.value)}
                />
              </div>
              <div className="search-input">
                <input
                  type="text"
                  placeholder="Search for salons or services..."
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                />
              </div>
              <button type="submit" className="search-icon" disabled={isLoading}>
                {isLoading ? 'Searching...' : 'Search'}
              </button>
            </form>
            {error && <p className="error-message">{error}</p>}
          </div>
        </section>
      </main>
      <ServiceList />
      <SalonList salons={salons} />
      <Recommendations />
      <Footer />
    </div>
  );
}

export default App;