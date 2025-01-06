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
import SearchResultsPage from './components/SearchResultsPage';
import { searchSalons } from './api';
import './App.css';

function App() {
    const [searchQuery, setSearchQuery] = useState('');
    const [location, setLocation] = useState('');
    const [salons, setSalons] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    const indianStates = [
      'Andhra Pradesh', 'Arunachal Pradesh', 'Assam', 'Bihar', 'Chhattisgarh',
      'Goa', 'Gujarat', 'Haryana', 'Himachal Pradesh', 'Jharkhand', 'Karnataka',
      'Kerala', 'Madhya Pradesh', 'Maharashtra', 'Manipur', 'Meghalaya',
      'Mizoram', 'Nagaland', 'Odisha', 'Punjab', 'Rajasthan', 'Sikkim',
      'Tamil Nadu', 'Telangana', 'Tripura', 'Uttar Pradesh', 'Uttarakhand',
      'West Bengal', 'Delhi', 'Jammu and Kashmir', 'Ladakh', 'Puducherry',
      'Andaman and Nicobar Islands', 'Chandigarh', 'Dadra and Nagar Haveli and Daman and Diu', 'Lakshadweep'
    ];

    const handleSearch = async (e) => {
      e.preventDefault();
      setIsLoading(true);
      setError(null);

      console.log("Search Query: ", searchQuery);
      console.log("Location: ", location);

       let query = "";

        if (searchQuery && location) {
          query = `location=${encodeURIComponent(location)}&service=${encodeURIComponent(searchQuery)}`;
        } else if (searchQuery) {
          query = `salon=${encodeURIComponent(searchQuery)}`;
        } else if (location) {
          query = `location=${encodeURIComponent(location)}`;
        }

      const apiEndpoint = `http://localhost:8080/salon/search?${query.toString()}`;
      console.log("API Endpoint: ", apiEndpoint);

      try {
        const response = await fetch(apiEndpoint);

        if (!response.ok) {
          throw new Error("Failed to fetch salons");
        }

        const textResponse = await response.text();
        console.log("Raw Response: ", textResponse);

        // Safely parse JSON if the response body is not empty
        const result = textResponse ? JSON.parse(textResponse) : [];
        console.log("Parsed Response: ", result);

        if (result.length === 0) {
          setError("No salons found matching your criteria.");
        }

        setSalons(result);
      } catch (error) {
        console.error("Error fetching data:", error);
        setError(
          "An error occurred while searching for salons. Please try again."
        );
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
  };

  return (
    <div className="app">
      <Router>
        <Navbar />
        <Routes>
          <Route path="/salon-page" element={<SalonPartnerPage />} />
          <Route path="/add-salon-page" element={<AddSalon />} />
          <Route path="/booking-form/:salonId" element={<BookAppointment />} />
          <Route path="/upload-documents-page/:salonId" element={<UploadDocuments />} />
          <Route path="/document-verification-page" element={<DocumentVerification />} />
          <Route path="/search-bar" element={<SearchBar />} />
          <Route path="/search-results" element={<SearchResultsPage />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signin" element={<Signin />} />
        </Routes>
        <main>
          <section className="hero">
            <div className="hero-content">
              <h1>Salon-It</h1>
              <h2>Find the perfect salon</h2>
              <form onSubmit={handleSearch} className="search-form">
                <div className="location-input">
                  <select
                    value={location}
                    onChange={(e) => setLocation(e.target.value)}
                  >
                    <option value="" disabled>Select Location</option>
                    {indianStates.map((state, index) => (
                      <option key={index} value={state}>
                        {state}
                      </option>
                    ))}
                  </select>
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
          <section className="search-results-section">
                      {isLoading && <p>Loading salons...</p>}
                      {!isLoading && salons.length > 0 && <SearchResultsPage salons={salons} />}
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