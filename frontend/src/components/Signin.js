import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios'; // Import Axios for API requests

const SignupPage = () => {
  const [userType, setUserType] = useState('customer');
  const [signupMethod, setSignupMethod] = useState('email');
  const [formData, setFormData] = useState({
    username: '',
    fullName: '',
    password: '',
    salonName: '',
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const signupRequest = {
        username: formData.username, // Username is either email or phone
        password: formData.password,
        userType,
      };
      const response = await axios.post('http://localhost:8080/signup/signup', signupRequest);
      alert(response.data);
      if (response.data === "Account created successfully!") {
              navigate('/login'); // Navigate to login page
            }
    } catch (error) {
      console.error('Signup failed:', error);
      alert('Error signing up. Please try again.');
    }
  };

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
          className={`signup-toggle-btn ${signupMethod === 'email' ? 'signup-active' : ''}`}
          onClick={() => setSignupMethod('email')}
        >
          Email
        </button>
        <button
          className={`signup-toggle-btn ${signupMethod === 'phone' ? 'signup-active' : ''}`}
          onClick={() => setSignupMethod('phone')}
        >
          Phone
        </button>
      </div>
      <form className="signup-form" onSubmit={handleSubmit}>
        <input
          type={signupMethod === 'email' ? 'email' : 'tel'}
          name="username"
          placeholder={signupMethod === 'email' ? 'Email' : 'Phone Number'}
          required
          className="signup-input"
          value={formData.username}
          onChange={handleChange}
        />
        <input
          type="text"
          name="fullName"
          placeholder="Full Name"
          required
          className="signup-input"
          value={formData.fullName}
          onChange={handleChange}
        />
        {userType === 'salonOwner' && (
          <input
            type="text"
            name="salonName"
            placeholder="Salon Name"
            required
            className="signup-input"
            value={formData.salonName}
            onChange={handleChange}
          />
        )}
        <input
          type="password"
          name="password"
          placeholder="Password"
          required
          className="signup-input"
          value={formData.password}
          onChange={handleChange}
        />
        <button type="submit" className="signup-submit-btn">Sign Up</button>
      </form>
    </div>
  );
};

export default SignupPage;
