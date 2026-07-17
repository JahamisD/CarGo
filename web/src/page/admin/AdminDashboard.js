import React from "react";
import AdminNavbar from "./AdminNavbar";

export default function AdminDashboard({user, handleLogout}){

    return(
        <>
            <AdminNavbar 
                user={user}
                handleLogout={handleLogout}
            />

            <div className="container mt-4">
                <h1>Admin Dashboard Test</h1>

                <p>
                    Welcome, {user.firstName}
                </p>

                <div className="row mt-4">

                    <div className="col-md-4">
                        <div className="card shadow">
                            <div className="card-body">
                                <h5 className="card-title">
                                    Manage Cars
                                </h5>
                                <p className="card-text">
                                    Add, edit, or remove cars.
                                </p>
                            </div>
                        </div>
                    </div>


                    <div className="col-md-4">
                        <div className="card shadow">
                            <div className="card-body">
                                <h5 className="card-title">
                                    Booking Requests
                                </h5>
                                <p className="card-text">
                                    Review customer bookings.
                                </p>
                            </div>
                        </div>
                    </div>


                    <div className="col-md-4">
                        <div className="card shadow">
                            <div className="card-body">
                                <h5 className="card-title">
                                    Profile
                                </h5>
                                <p className="card-text">
                                    Manage admin information.
                                </p>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
        </>
    );
}