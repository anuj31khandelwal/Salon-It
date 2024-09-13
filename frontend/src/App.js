import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import SearchBar from './components/SearchBar';
import SalonList from './components/SalonList';
import ServiceList from './components/ServiceList';
import MobileView from './components/MobileView';
import Recommendations from './components/Recommendations';
import Footer from './components/Footer';
import './App.css';  // Single CSS file

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <SearchBar/>
        <ServiceList />
        <Routes>
        <Route path="/services" element={<ServiceList />} />
          <Route path="/" element={<SalonList />} />
          <Route path="/services" element={<ServiceList />} />
          <Route path="/mobile" element={<MobileView />} />
        </Routes>
        <Recommendations/>
        <Footer/>
      </div>
    </Router>
  );
}

export default App;
