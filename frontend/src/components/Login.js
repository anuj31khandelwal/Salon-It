import React, { useState } from 'react';
import { login } from '../api';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
  const [userType, setUserType] = useState('Customer');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setErrorMessage('');

    try {
      const response = await login(username, password, userType);
      if (response.status === 200) {
        console.log('Login successful!');
        const userData = response.data; // Assuming the API sends back user data
        console.log("data sent:", userData);
        localStorage.setItem('userData', JSON.stringify(userData)); // Save user data to localStorage
        navigate('/dashboard');
      }
    } catch (error) {
      if (error.response && error.response.status === 401) {
        setErrorMessage('Invalid username or password');
      } else {
        setErrorMessage('An error occurred. Please try again later.');
      }
      console.error('Error logging in:', error);
    }
  };

  return (
    <div className="login-wrapper">
      <h2 className="login-title">Login</h2>
      <div className="login-user-type-toggle">
        <button
          className={`login-toggle-btn ${userType === 'customer' ? 'login-active' : ''}`}
          onClick={() => setUserType('customer')}
        >
          Customer
        </button>
        <button
          className={`login-toggle-btn ${userType === 'salonOwner' ? 'login-active' : ''}`}
          onClick={() => setUserType('salonOwner')}
        >
          Salon Owner
        </button>
      </div>

      {errorMessage && <div className="error-message">{errorMessage}</div>}

      <form className="login-form" onSubmit={handleLogin}>
        <input
          type="text"
          placeholder="Username"
          required
          className="login-input"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          placeholder="Password"
          required
          className="login-input"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit" className="login-submit-btn">Login</button>
      </form>
    </div>
  );
};

export default LoginPage;
