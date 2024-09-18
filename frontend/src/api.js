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

export default api;