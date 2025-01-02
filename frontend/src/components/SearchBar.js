import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const SearchPage = () => {
  const [location, setLocation] = useState('');
  const [service, setService] = useState('');
  const [salon, setSalon] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  // API call to search salons
  const searchSalons = async () => {
    setIsLoading(true);
    setErrorMessage('');

    console.log('Inputs before API call:', { salon, location, service });

    // Prepare query params for the API
    const params = new URLSearchParams();
    if (salon) params.append('salon', salon);
    if (location) params.append('location', location);
    if (service) params.append('service', service);

    console.log('Query string being sent to API:', params.toString());

    try {
      // Instead of fetching, just navigate to the results page with query params
      navigate(`/search-results?${params.toString()}`);
    } catch (error) {
      console.error('Error during search:', error);
      setErrorMessage('An error occurred. Please try again.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="search-bar">
      <input
        type="text"
        placeholder="Search by salon name"
        value={salon}
        onChange={(e) => setSalon(e.target.value)}
      />
      <input
        type="text"
        placeholder="Enter location"
        value={location}
        onChange={(e) => setLocation(e.target.value)}
      />
      <input
        type="text"
        placeholder="Enter service"
        value={service}
        onChange={(e) => setService(e.target.value)}
      />
      <button onClick={searchSalons} disabled={isLoading}>
        {isLoading ? 'Searching...' : 'Search'}
      </button>

      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
    </div>
  );
};

export default SearchPage;
