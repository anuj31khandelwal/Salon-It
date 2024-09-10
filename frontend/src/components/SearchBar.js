import React from 'react';

function SearchBar({ searchTerm, onSearch }) {
  return (
    <input
      type="text"
      value={searchTerm}
      onChange={onSearch}
      placeholder="Search for services or salons..."
    />
  );
}

export default SearchBar;
