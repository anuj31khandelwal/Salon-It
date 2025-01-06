import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const statesOfIndia = [
  'Andhra Pradesh',
  'Arunachal Pradesh',
  'Assam',
  'Bihar',
  'Chhattisgarh',
  'Goa',
  'Gujarat',
  'Haryana',
  'Himachal Pradesh',
  'Jharkhand',
  'Karnataka',
  'Kerala',
  'Madhya Pradesh',
  'Maharashtra',
  'Manipur',
  'Meghalaya',
  'Mizoram',
  'Nagaland',
  'Odisha',
  'Punjab',
  'Rajasthan',
  'Sikkim',
  'Tamil Nadu',
  'Telangana',
  'Tripura',
  'Uttar Pradesh',
  'Uttarakhand',
  'West Bengal',
];

const SearchPage = () => {
  const [location, setLocation] = useState('');
  const [service, setService] = useState('');
  const [salon, setSalon] = useState('');
  const [salons, setSalons] = useState([]);
  const navigate = useNavigate();

  // API call to search salons
  const searchSalons = async () => {
    try {
      let query = '';
      if (salon) query += `salon=${salon}&`;
      if (location) query += `location=${location}&`;
      if (service) query += `service=${service}&`;

      const response = await fetch(`http://localhost:8080/search?${query}`);
      const result = await response.json();

      if (result) {
        setSalons(result);
      }
    } catch (error) {
      console.error('Error searching salons:', error);
    }
  };

  // Handle clicking a salon and navigating to its details
  const handleSalonClick = (salonId) => {
    navigate(`/salon/${salonId}`);
  };

  return (
    <div>
      <input
        type="text"
        placeholder="Search by salon name"
        value={salon}
        onChange={(e) => setSalon(e.target.value)}
      />
      <select value={location} onChange={(e) => setLocation(e.target.value)}>
        <option value="">Select a location</option>
        {statesOfIndia.map((state, index) => (
          <option key={index} value={state}>
            {state}
          </option>
        ))}
      </select>
      <input
        type="text"
        placeholder="Enter service"
        value={service}
        onChange={(e) => setService(e.target.value)}
      />
      <button onClick={searchSalons}>Search</button>

      <div>
        {salons.length === 0 ? (
          <p>No salons found</p>
        ) : (
          salons.map((salon) => (
            <div key={salon.id} onClick={() => handleSalonClick(salon.id)}>
              <h3>{salon.name}</h3>
              <p>{salon.location}</p>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default SearchPage;
