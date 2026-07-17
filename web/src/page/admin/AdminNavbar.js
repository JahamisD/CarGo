import React from "react";
import { Link, useNavigate } from "react-router-dom";

export default function AdminNavbar(props){

    const navigate = useNavigate();

    function logoutClick(){
    props.handleLogout();

    alert("Logged out successfully");
    navigate('/cars', { replace: true });
    }

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <div className="container-fluid">

                <Link className="navbar-brand" to="/admin">
                    CarGo Admin
                </Link>

                <div className="navbar-nav">

                    <Link 
                        className="nav-link" 
                        to="/admin"
                    >
                        Dashboard
                    </Link>

                    <Link 
                        className="nav-link" 
                        to="/admin/profile"
                    >
                        Profile
                    </Link>

                    <Link 
                        className="nav-link" 
                        to="/admin/cars"
                    >
                        Manage Cars
                    </Link>

                    <Link 
                        className="nav-link" 
                        to="/admin/bookings"
                    >
                        Booking Requests
                    </Link>

                </div>

                <div>
                    <span className="text-white me-3">
                        Hi, {props.user.firstName}
                    </span>

                    <button 
                        className="btn btn-outline-light btn-sm"
                        onClick={logoutClick}
                    >
                        Logout
                    </button>
                </div>

            </div>
        </nav>
    );
}