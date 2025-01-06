import React, { useState, useEffect, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import { bookAppointment, getSalonServices } from '../api';

const BookAppointment = () => {
  const { salonId } = useParams(); // Get salonId from the route params
  console.log("Fetched salonId from params:", salonId); // Debugging

  const [services, setServices] = useState([]);
  const [formData, setFormData] = useState({
    salonId: '',
    serviceId: '',
    appointmentTime: '',
    customerName: '',
    customerEmail: '',
  });
  const [loading, setLoading] = useState(false);
  const [servicesLoading, setServicesLoading] = useState(true);
  const [error, setError] = useState(null);

  // Initialize salonId in form data
  useEffect(() => {
    if (salonId) {
      setFormData((prevFormData) => ({ ...prevFormData, salonId }));
    }
  }, [salonId]);

  const loadServices = useCallback(async () => {
    if (!salonId) {
      console.error("salonId is undefined");
      setError("Salon ID is missing. Unable to fetch services.");
      return;
    }

    setServicesLoading(true);
    setError(null);

    try {
      console.log(`Fetching services for salonId: ${salonId}`);
      const response = await getSalonServices(salonId);
      console.log('API Response:', response);

      if (Array.isArray(response)) {
        setServices(response);
      } else if (response && Array.isArray(response.data)) {
        setServices(response.data);
      } else {
        throw new Error('Invalid response structure');
      }
    } catch (err) {
      console.error('Error loading services:', err.message || err);
      setError('Failed to load services. Please try again.');
    } finally {
      setServicesLoading(false);
    }
  }, [salonId]);

  useEffect(() => {
    if (salonId) {
      console.log(`Fetching services for salonId: ${salonId}`);
      loadServices();
    } else {
      console.error('salonId is undefined in useParams.');
    }
  }, [salonId, loadServices]);


  const handleInputChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    const payload = {
      salonId: parseInt(formData.salonId, 10),
      serviceId: parseInt(formData.serviceId, 10),
      appointmentTime: formData.appointmentTime,
      customerName: formData.customerName,
      customerEmail: formData.customerEmail,
    };

    try {
      console.log('Payload for booking:', payload);
      await bookAppointment(payload);
      alert('Appointment booked successfully!');
      setFormData({
        salonId: salonId,
        serviceId: '',
        appointmentTime: '',
        customerName: '',
        customerEmail: '',
      });
    } catch (err) {
      console.error('Error booking appointment:', err);
      const errorResponse = err.response ? err.response.data : err.message;
      setError(`Failed to book appointment. ${errorResponse}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="booking-form">
      <h2>Book Appointment</h2>
      {error && <div className="error">{error}</div>}
      {servicesLoading && <div>Loading services...</div>}
      {!servicesLoading && services.length === 0 && <div>No services available for this salon.</div>}
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="serviceId">Select Service:</label>
          <select
            id="serviceId"
            name="serviceId"
            aria-label="Select a service"
            value={formData.serviceId}
            onChange={handleInputChange}
            required
          >
            <option value="">Select a service</option>
            {services.map((service) => (
              <option key={service.id} value={service.id}>
                {service.name} - ₹{service.price.toFixed(2)}
              </option>
            ))}
          </select>
        </div>
        <div>
          <label htmlFor="appointmentTime">Appointment Time:</label>
          <input
            id="appointmentTime"
            type="datetime-local"
            name="appointmentTime"
            value={formData.appointmentTime}
            onChange={handleInputChange}
            required
          />
        </div>
        <div>
          <label htmlFor="customerName">Your Name:</label>
          <input
            id="customerName"
            type="text"
            name="customerName"
            value={formData.customerName}
            onChange={handleInputChange}
            required
          />
        </div>
        <div>
          <label htmlFor="customerEmail">Your Email:</label>
          <input
            id="customerEmail"
            type="email"
            name="customerEmail"
            value={formData.customerEmail}
            onChange={handleInputChange}
            required
          />
        </div>
        <button type="submit" disabled={loading || servicesLoading}>
          {loading ? 'Booking...' : 'Book Appointment'}
        </button>
      </form>
    </div>
  );
};

export default BookAppointment;
