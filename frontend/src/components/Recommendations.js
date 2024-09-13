import React from 'react';

const localities = [
  { name: 'Maharana Pratap Nagar', places: 454 },
  { name: 'TT Nagar', places: 319 },
  { name: 'Arera Colony', places: 272 },
  { name: 'Gulmohar Colony', places: 338 },
  { name: 'Kohefiza', places: 191 },
  { name: 'Peer Gate Area', places: 223 },
  { name: 'Habib Ganj', places: 130 },
  { name: 'BHEL', places: 541 },
];

const Recommendations = () => {
  return (
    <div className="recommendations">
      <h2 className="recommendations-title">Popular localities in and around Bhopal</h2>
      <div className="recommendations-grid">
        {localities.map((locality, index) => (
          <div key={index} className="locality-card">
            <div className="locality-info">
              <h3>{locality.name}</h3>
              <p>{locality.places} places</p>
            </div>
            <span className="arrow">&gt;</span>
          </div>
        ))}
        <div className="locality-card see-more">
          <span>see more</span>
          <span className="arrow down">v</span>
        </div>
      </div>
    </div>
  );
};

export default Recommendations;