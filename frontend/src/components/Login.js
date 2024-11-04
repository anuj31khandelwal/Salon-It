import React, { useState } from 'react';
import axios from 'axios';

const LoginPage = () => {
  const [userType, setUserType] = useState('customer');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    setErrorMessage(''); // Clear any previous error messages

    try {
      const response = await axios.post('http://localhost:8080/authorization/login', {
        username,
        password,
        userType, // You may need this if backend handles different user types
      }, { withCredentials: true }); // Include credentials for session handling

      // Handle successful login (redirect or display message)
      if (response.status === 200) {
        console.log('Login successful!');
        // Redirect to the dashboard or home page
        window.location.href = '/home';
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
