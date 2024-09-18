import React from 'react';
import { Facebook, Instagram, Twitter, Youtube, Linkedin } from 'lucide-react';

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-container">
        <div className="footer-sections">
          <div className="footer-section">
            <h3>About Us</h3>
            <ul>
              <li><a href="#">Who We Are</a></li>
              <li><a href="#">Blog</a></li>
              <li><a href="#">Work With Us</a></li>
              <li><a href="#">Investor Relations</a></li>
              <li><a href="#">Report Fraud</a></li>
              <li><a href="#">Contact Us</a></li>
            </ul>
          </div>
          <div className="footer-section">
            <h3>Our Services</h3>
            <ul>
              <li><a href="#">Hair Styling</a></li>
              <li><a href="#">Nail Care</a></li>
              <li><a href="#">Makeup</a></li>
              <li><a href="#">Spa Treatments</a></li>
              <li><a href="#">Waxing</a></li>
            </ul>
          </div>
          <div className="footer-section">
            <h3>For Stylists</h3>
            <ul>
              <li><a href="#">Partner With Us</a></li>
              <li><a href="#">Apps For You</a></li>
            </ul>
          </div>
          <div className="footer-section">
            <h3>Learn More</h3>
            <ul>
              <li><a href="#">Privacy</a></li>
              <li><a href="#">Security</a></li>
              <li><a href="#">Terms</a></li>
              <li><a href="#">Sitemap</a></li>
            </ul>
          </div>
          <div className="footer-section">
            <h3>Social Links</h3>
            <div className="social-icons">
              <a href="#"><Linkedin size={20} /></a>
              <a href="#"><Instagram size={20} /></a>
              <a href="#"><Twitter size={20} /></a>
              <a href="#"><Youtube size={20} /></a>
              <a href="#"><Facebook size={20} /></a>
            </div>
          </div>
        </div>
        <div className="footer-bottom">
          <p>By continuing past this page, you agree to our Terms of Service, Cookie Policy, Privacy Policy and Content Policies. All trademarks are properties of their respective owners. 2008-2024 © YourSalonName™ Ltd. All rights reserved.</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;