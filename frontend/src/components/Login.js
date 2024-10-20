import React, { useState } from 'react';

const LoginPage = () => {
  const [userType, setUserType] = useState('customer');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = (e) => {
    e.preventDefault();
    // Replace this with your login logic, such as sending a request to your backend API
    console.log(`Logging in with Username: ${username}, Password: ${password}`);
    // Add success/failure handling logic
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
