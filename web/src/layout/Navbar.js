import React from 'react'
import { Link, useNavigate } from 'react-router-dom'

export default function Navbar(props) {
  const user = props.user;
  const navigate = useNavigate();

  function logoutClick() {
    props.handleLogout();
    navigate('/login');
  }

  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
        <div className="container-fluid">
          <Link className="navbar-brand" to="/cars">CarGo</Link>

          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>

          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <Link className="nav-link" to="/cars">Browse Cars</Link>
              </li>

              {/* only show these links if someone is logged in */}
              {user && (
                <li className="nav-item">
                  <Link className="nav-link" to="/my-bookings">My Bookings</Link>
                </li>
              )}
              {user && (
                <li className="nav-item">
                  <Link className="nav-link" to="/my-cars">My Cars</Link>
                </li>
              )}
            </ul>

            {user ? (
              <div>
                <span className="text-white me-2">Hi, {user.firstName}</span>
                <button className="btn btn-outline-light btn-sm" onClick={logoutClick}>Logout</button>
              </div>
            ) : (
              <div>
                <Link className="btn btn-outline-light btn-sm me-2" to="/login">Login</Link>
                <Link className="btn btn-light btn-sm" to="/register">Register</Link>
              </div>
            )}
          </div>
        </div>
      </nav>
    </div>
  )
}
