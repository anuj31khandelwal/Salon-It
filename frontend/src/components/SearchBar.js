import React from 'react';

const SearchBar = () => {
  return (
<div className="navbar-search">
        <input 
          type="text" 
          className="search-input" 
          placeholder="Search for salons or services..." 
        />
        <button type="submit" className="search-btn">Search</button>
      </div>
      );
    };
    
    export default SearchBar;
    