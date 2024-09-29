import React, { useState } from 'react';

const SignupPage = () => {
  const [userType, setUserType] = useState('customer');
  const [signupMethod, setSignupMethod] = useState('mobile');

  return (
    <div className="signup-wrapper">
      <h2 className="signup-title">Sign Up</h2>
      <div className="signup-user-type-toggle">
        <button
          className={`signup-toggle-btn ${userType === 'customer' ? 'signup-active' : ''}`}
          onClick={() => setUserType('customer')}
        >
          Customer
        </button>
        <button
          className={`signup-toggle-btn ${userType === 'salonOwner' ? 'signup-active' : ''}`}
          onClick={() => setUserType('salonOwner')}
        >
          Salon Owner
        </button>
      </div>
      <div className="signup-method-toggle">
        <button
          className={`signup-toggle-btn ${signupMethod === 'mobile' ? 'signup-active' : ''}`}
          onClick={() => setSignupMethod('mobile')}
        >
          Mobile
        </button>
        <button
          className={`signup-toggle-btn ${signupMethod === 'google' ? 'signup-active' : ''}`}
          onClick={() => setSignupMethod('google')}
        >
          Google
        </button>
      </div>
      {signupMethod === 'mobile' ? (
        <form className="signup-form">
          <input type="text" placeholder="Full Name" required className="signup-input" />
          <input type="email" placeholder="Email" required className="signup-input" />
          <input type="tel" placeholder="Mobile Number" required className="signup-input" />
          {userType === 'salonOwner' && (
            <input type="text" placeholder="Salon Name" required className="signup-input" />
          )}
          <button type="submit" className="signup-submit-btn">Sign Up</button>
        </form>
      ) : (
        <button className="signup-google-btn">Sign Up with Google</button>
      )}
    </div>
  );
};

export default SignupPage;