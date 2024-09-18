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

function Navbar() {
  return (
    <nav className="navbar">
      <div className="navbar-container">
        <div className="navbar-left">
          <img src="/assets/logo.jpg" alt="Salon-It Logo" className="navbar-logo" />
        </div>
        <div className="navbar-right">
          <a href="#" className="navbar-link">Investor Relations</a>
          <a href="#" className="navbar-link">Add salon</a>
          <a href="#" className="navbar-link">Log in</a>
          <a href="#" className="navbar-link">Sign up</a>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;