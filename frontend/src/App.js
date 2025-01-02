import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import AddSalon from './components/AddSalon';
import Login from './components/Login';
import Signin from './components/Signin';
import SearchBar from './components/SearchBar';
import SalonGrid from './components/SalonGrid';
import SalonList from './components/SalonList';
import SalonDetails from './components/SalonDetails';
import ServiceList from './components/ServiceList';
import MobileView from './components/MobileView';
import Recommendations from './components/Recommendations';
import BookAppointment from './components/BookAppointment';
import SalonPartnerPage from './components/SalonPartnerPage';
import Footer from './components/Footer';
import UploadDocuments from './components/UploadDocuments';
import DocumentVerification from './components/DocumentVerification';
import { searchSalons } from './api';
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

  const salonData = {
      name: "Glamour Studio",
      description: "Your one-stop shop for all beauty needs",
      rating: 4.5,
      address: "123 Main St, Anytown, USA",
      phone: "(123) 456-7890",
      hours: "Mon-Sat: 9AM-8PM",
      // Add other necessary salon data
    };

  return (
    <div className="app">
      <Router>
        <Navbar />
        <Routes>
          <Route path="/salon-page" element={<SalonPartnerPage />} />
          <Route path="/add-salon-page" element={<AddSalon />} />
          <Route path="/upload-documents-page/:salonId" element={<UploadDocuments />} />
          <Route path="/document-verification-page" element={<DocumentVerification />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signin" element={<Signin />} />
          <Route path="/search-bar" element={<SearchBar />} />
        </Routes>
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
        <Routes>
        <Route path="/salongrid" element={<SalonGrid />} />
        </Routes>
        <SalonDetails salon={salonData} />
        <Recommendations />
        <Footer />
      </Router>
    </div>
  );
}

export default App;
