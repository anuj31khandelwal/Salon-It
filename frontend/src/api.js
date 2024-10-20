import axios from 'axios';

const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
});

export const searchSalons = async (location, service) => {
  try {
    const response = await api.get('/salon', {
      params: { location, service }
    });
    return response.data;
  } catch (error) {
    console.error('API call error:', error);
    throw error;
  }
};

// Function to call your backend API for booking an appointment
export const bookAppointment = async (salonId, appointmentTime) => {
  const response = await axios.post('/customer/book', {
    salonId,
    appointmentTime,
  });
  return response.data; // Ensure this matches the response from your backend
};

export const confirmAppointment = async (appointmentId) => {
  try {
    const response = await axios.put(`/salon/confirm-appointment/${appointmentId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export default api;