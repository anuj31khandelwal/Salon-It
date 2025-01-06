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
  console.log(`getSalonServices called with salonId: ${salonId}`);
  try {
    if (!salonId) {
      throw new Error('Salon ID is required to fetch services.');
    }

    const response = await axios.get(`http://localhost:8080/salon/${salonId}/services`);
    console.log('API Response:', response.data);

    // Ensure the response structure is as expected
    if (response && response.data) {
      return response.data;
    } else {
      throw new Error('Invalid response format from API.');
    }
  } catch (error) {
    console.error('Error in getSalonServices:', error.message || error);
    throw error;
  }
};

//export const bookAppointment = async (appointmentData) => {
//    try {
//        const response = await api.post('/api/appointments/book', appointmentData);
//        return response.data;
//    } catch (error) {
//        throw error;
//    }
//};
// api.js


//export const getSalonServices = async (salonId) => {
//    const response = await axios.get(`http://localhost:8080/api/salons/${salonId}/services`);
//    return response.data;
//};

export const bookAppointment = async (payload) => {
    const response = await axios.post('http://localhost:8080/api/appointments/book', payload);
    return response.data;
};

//export const bookAppointment = async (appointmentRequest) => {
//    try {
//        const response = await axios.post('http://localhost:8080/api/appointments/book', appointmentRequest);
//        return response.data; // Return the booked appointment data
//    } catch (error) {
//        console.error('Error booking appointment:', error);
//        throw error; // Re-throw the error to be handled in the calling function
//    }
//};

export const searchSalons = async (searchParams) => {
  try {
    // Construct the query based on the parameters provided
    const response = await api.get('/salon/search', {
      params: searchParams // Pass the dynamic search parameters (location, service, salon)
    });
    return response.data;
  } catch (error) {
    console.error('Error in API call:', error);
    throw error;
  }
};

//const api = axios.create({
//  baseURL: process.env.REACT_APP_API_URL,
//});

const BASE_URL = 'http://localhost:8080/authorization';

// Login function
export const login = async (username, password, userType) => {
  try {
  console.log('Login Request Data:');
      console.log('Username:', username);
      console.log('Password:', password);
      console.log('UserType:', userType);
    const response = await axios.post(
      `${BASE_URL}/login`,
      { username, password, userType },
      { withCredentials: true } // Include credentials for session handling
    );
    return response; // Return response to be handled in the calling component
  } catch (error) {
    throw error; // Throw error to be caught in the calling component
  }
};

// Example: Get home page data or any other API call
export const getHomePage = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/home`);
    return response.data;
  } catch (error) {
    throw error;
  }
};


//export const searchSalons = async (location, service) => {
//  try {
//    const response = await api.get('/salon', {
//      params: { location, service }
//    });
//    return response.data;
//  } catch (error) {
//    console.error('API call error:', error);
//    throw error;
//  }
//};

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