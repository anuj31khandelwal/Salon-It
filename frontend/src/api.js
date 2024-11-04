import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

// api.js
export const getSalonServices = async (salonId) => {
    const response = await fetch(`http://localhost:8080/salon/${salonId}/services`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    if (!response.ok) {
        throw new Error('Failed to fetch services');
    }

    const result = await response.json();
    // Assuming your API returns the services under a specific property, adjust accordingly
    return result.data; // Adjust this based on your actual response structure
};

//export const bookAppointment = async (appointmentData) => {
//    try {
//        const response = await api.post('/api/appointments/book', appointmentData);
//        return response.data;
//    } catch (error) {
//        throw error;
//    }
//};
export const bookAppointment = async (appointmentRequest) => {
    try {
        const response = await axios.post('http://localhost:8080/api/appointments/book', appointmentRequest);
        return response.data; // Return the booked appointment data
    } catch (error) {
        console.error('Error booking appointment:', error);
        throw error; // Re-throw the error to be handled in the calling function
    }
};

//export const getSalonServices = async (salonId) => {
//    try {
//        const response = await api.get(`/salons/${salonId}/services`);
//        return response.data;
//    } catch (error) {
//        throw error;
//    }
//};

//const api = axios.create({
//  baseURL: process.env.REACT_APP_API_URL,
//});

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

//export const bookAppointment = async (appointmentData) => {
//  return await axios.post(`process.env.REACT_APP_API_URL/appointments`, {
//    salonId: appointmentData.salonId,
//    serviceId: appointmentData.serviceId,
//    appointmentTime: appointmentData.appointmentTime,
//    customerName: appointmentData.customerName,
//    customerEmail: appointmentData.customerEmail
//  });
//};
//export const bookAppointment = async (salonId, appointmentTime) => {
//  const response = await axios.post('/customer/book', {
//    salonId,
//    appointmentTime,
//  });
//  return response.data; // Ensure this matches the response from your backend
//};

export const confirmAppointment = async (appointmentId) => {
  try {
    const response = await axios.put(`/salon/confirm-appointment/${appointmentId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export default api;