import React from 'react';
import SalonList from './SalonList';
import ServiceList from './ServiceList';

const MobileView = () => {
  return (
    <div className="mobile-view">
      <SalonList />
      <ServiceList />
    </div>
  );
};

export default MobileView;
