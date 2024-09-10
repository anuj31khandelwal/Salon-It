import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import SalonList from './components/SalonList';
import ServiceList from './components/ServiceList';
import MobileView from './components/MobileView';
import './App.css';  // Single CSS file

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Routes>
          <Route path="/" element={<SalonList />} />
          <Route path="/services" element={<ServiceList />} />
          <Route path="/mobile" element={<MobileView />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
