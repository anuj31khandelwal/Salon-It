// In BookAppointment.js
import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import { bookAppointment } from '../api'; // Import your booking API function

const BookAppointment = () => {
  const { salonId } = useParams(); // Get the salon ID from the URL
  const [serviceId, setServiceId] = useState('');
  const [appointmentTime, setAppointmentTime] = useState('');
  const [confirmation, setConfirmation] = useState(null);

  const handleBookAppointment = async () => {
    const appointmentData = {
      salonId: salonId,
      serviceId: serviceId,
      appointmentTime: appointmentTime,
      confirmed: false
    };

    try {
      const response = await bookAppointment(appointmentData);
      setConfirmation(response.data);
    } catch (error) {
      console.error('Error booking appointment:', error);
    }
  };

  return (
    <div>
      <h2>Book Appointment at Salon {salonId}</h2>
      <label>
        Select Service:
        <input type="text" value={serviceId} onChange={(e) => setServiceId(e.target.value)} />
      </label>
      <label>
        Appointment Time:
        <input type="datetime-local" value={appointmentTime} onChange={(e) => setAppointmentTime(e.target.value)} />
      </label>
      <button onClick={handleBookAppointment}>Book Appointment</button>
      {confirmation && <p>Appointment booked successfully! Confirmation ID: {confirmation.id}</p>}
    </div>
  );
};

export default BookAppointment;
