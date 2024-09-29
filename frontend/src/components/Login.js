import React, { useState } from 'react';

const LoginPage = () => {
  const [userType, setUserType] = useState('customer');
  const [loginMethod, setLoginMethod] = useState('mobile');

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
      <div className="login-method-toggle">
        <button
          className={`login-toggle-btn ${loginMethod === 'mobile' ? 'login-active' : ''}`}
          onClick={() => setLoginMethod('mobile')}
        >
          Mobile
        </button>
        <button
          className={`login-toggle-btn ${loginMethod === 'google' ? 'login-active' : ''}`}
          onClick={() => setLoginMethod('google')}
        >
          Google
        </button>
      </div>
      {loginMethod === 'mobile' ? (
        <form className="login-form">
          <input type="tel" placeholder="Mobile Number" required className="login-input" />
          <button type="submit" className="login-submit-btn">Send OTP</button>
        </form>
      ) : (
        <button className="login-google-btn">Login with Google</button>
      )}
    </div>
  );
};

export default LoginPage;