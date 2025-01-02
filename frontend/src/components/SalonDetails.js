import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const SalonDetailsPage = () => {
  const { salonId } = useParams(); // Get salonId from the URL
  const [salon, setSalon] = useState(null);
  const [services, setServices] = useState([]);

  useEffect(() => {
    const fetchSalonDetails = async () => {
      console.log('Fetching salon details...');

      try {
        // Fetch salon details by salonId
        const salonResponse = await fetch(`http://localhost:8080/salon/${salonId}`);
        const salonData = await salonResponse.json();

        console.log('Salon data:', salonData); // Log the fetched salon data
        setSalon(salonData);

        // Fetch services for this salon
        const servicesResponse = await fetch(`http://localhost:8080/salon/${salonId}/services`);
        const servicesData = await servicesResponse.json();

        console.log('Services data:', servicesData); // Log the fetched services data

        // Ensure the services data is an array
        if (Array.isArray(servicesData)) {
          setServices(servicesData);
        } else {
          console.error('Received services data is not an array:', servicesData);
        }
      } catch (error) {
        console.error('Error fetching salon details:', error);
      }
    };

    fetchSalonDetails();
  }, [salonId]);

  if (!salon) return <div>Loading...</div>;

  return (
    <div>
      <h2>{salon.name}</h2>
      <p>{salon.location}</p>

      <h3>Services Offered:</h3>
      <ul>
        {services.length === 0 ? (
          <p>No services found</p>
        ) : (
          services.map((service) => (
            <li key={service.id}>{service.name}</li>
          ))
        )}
      </ul>
    </div>
  );
};

export default SalonDetailsPage;
