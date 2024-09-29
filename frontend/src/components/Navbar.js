// import React from 'react';

// const Navbar = () => {
//   return (
//     <nav className="navbar">
//       <div className="navbar-logo">
//         <img src="/assets/logo.jpg" alt="Salon-It!" className="logo-image" />
//       </div>
//       <div className="navbar-links">
//         <a href="/Home">Home</a>
//         <a href="About">About</a>
//         <a href="Invest">Invester</a>
//         <a href="/login">Log in</a>
//         <a href="/signup">Sign up</a>
//       </div>
//     </nav>
//   );
// };

// export default Navbar;
import React from 'react';
import {Link} from 'react-router-dom';

function Navbar() {
  return (
    <nav className="navbar">
      <div className="navbar-container">
        <div className="navbar-left">
          <img src="/assets/logo.jpg" alt="Salon-It Logo" className="navbar-logo" />
        </div>
        <div className="navbar-right">
          <Link to="/investor-relations" className="navbar-link">Investor Relations</Link>
          <Link to="/add-salon" className="navbar-link">Add Salon</Link>
          <Link to="/login" className="navbar-link">Login</Link>
          <Link to="/signin" className="navbar-link">Sign In</Link>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;