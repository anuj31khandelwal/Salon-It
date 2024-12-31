import React, { useState } from 'react';
import { searchSalons } from '../api'; // Import the search API

const SalonSearch = () => {
  const [searchTerm, setSearchTerm] = useState('');  // Initialize search term state
  const [location, setLocation] = useState('');      // Initialize location state
  const [service, setService] = useState('');        // Initialize service state

  // Function to handle input change in the search bar
  const handleSearchTermChange = (event) => {
    setSearchTerm(event.target.value);  // Update the search term state based on user input
  };

  // Function to handle form submission or search trigger
  const handleSearch = async () => {
    try {
      // If search term is empty, set the location and service params to empty
      let searchParams = { location: '', service: '', salon: '' };

      // If search term contains a location or service, set those params
      if (location) {
        searchParams.location = location;
      }
      if (service) {
        searchParams.service = service;
      }
      if (searchTerm) {
        searchParams.salon = searchTerm;  // If user enters a salon name, set it as a salon search parameter
      }

      // Call searchSalons API with the appropriate search parameters
      const salons = await searchSalons(searchParams);
      console.log(salons); // You can store or display the results in the UI
    } catch (error) {
      console.error('Error searching salons:', error);
    }
  };

  return (
    <div>
      <input
        type="text"
        placeholder="Search salon, location, or service"
        value={searchTerm}
        onChange={handleSearchTermChange} // Update search term when user types
      />
      <input
        type="text"
        placeholder="Enter location"
        value={location}
        onChange={(e) => setLocation(e.target.value)} // Update location when user types
      />
      <input
        type="text"
        placeholder="Enter service"
        value={service}
        onChange={(e) => setService(e.target.value)} // Update service when user types
      />
      <button onClick={handleSearch}>Search</button>
    </div>
  );
};

export default SalonSearch;
