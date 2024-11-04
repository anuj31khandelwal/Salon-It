import React, { useState, useEffect, useCallback } from 'react';
import { bookAppointment, getSalonServices } from '../api';

const BookAppointment = ({ salonId }) => {
    const [services, setServices] = useState([]);
    const [formData, setFormData] = useState({
        salonId: '',
        serviceId: '',
        appointmentTime: '',
        customerName: '',
        customerEmail: ''
    });
    const [loading, setLoading] = useState(false);
    const [servicesLoading, setServicesLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        setFormData(prevFormData => ({ ...prevFormData, salonId }));
    }, [salonId]);

    const loadServices = useCallback(async () => {
        setServicesLoading(true);
        setError(null);
        try {
            const response = await getSalonServices(salonId);
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
        loadServices();
    }, [loadServices]);

    const handleInputChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };
    <select
        name="serviceId"
        onChange={handleInputChange}
    >
        <option value="">Select a service</option>
        {services.map(service => (
            <option key={service.id} value={service.id}> {/* Ensure unique key here */}
                {service.name}
            </option>
        ))}
    </select>



const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    // Prepare the payload with the correct types
    const payload = {
        salonId: parseInt(formData.salonId, 10), // Ensure it's a number (Long)
        serviceId: parseInt(formData.serviceId, 10), // Ensure it's a number (Long)
        appointmentTime: formData.appointmentTime,
        customerName: formData.customerName,
        customerEmail: formData.customerEmail
    };

console.log('Selected service ID:', formData.serviceId);

    try {
        console.log('Payload for booking:', payload); // Debugging
        await bookAppointment(payload);
        alert('Appointment booked successfully!');

        // Reset form data after successful booking
        setFormData({
            salonId: salonId,
            serviceId: '',
            appointmentTime: '',
            customerName: '',
            customerEmail: ''
        });
    } catch (err) {
        console.error('Error booking appointment:', err); // Debugging
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
                        {Array.isArray(services) && services.map(service => (
                            <option key={service.id} value={service.id}>
                                {service.name} - ${service.price}
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
