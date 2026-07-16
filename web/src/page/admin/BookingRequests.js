import React, { useEffect, useState } from "react";
import API_URL from "../../apiUrl";
import AdminNavbar from "./AdminNavbar";

export default function BookingRequests({ user, handleLogout }) {

    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);


    function loadBookings(){
        setLoading(true);
        fetch(API_URL + "/bookings/pending")
            .then((res) => res.json())
            .then((data) => {
                setBookings(Array.isArray(data) ? data : []);
                setLoading(false);
            })
            .catch((err) => {
                console.log(err);
                setLoading(false);
            });
    }


    useEffect(() => {
        loadBookings();
    }, []);

    function updateStatus(id, status){
        fetch(API_URL + "/bookings/" + id + "/status", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                status: status
            })
        })
        .then((res) => res.json())
        .then(() => {

            alert(
                status === "CONFIRMED"
                ? "Booking confirmed"
                : "Booking rejected"
            );
            loadBookings();
        })
        .catch((err) => {
            console.log(err);
            alert("Something went wrong");
        });
    }



    return (
        <>
            <AdminNavbar
                user={user}
                handleLogout={handleLogout}
            />
            <div className="container mt-4">
                <h2>
                    Booking Requests
                </h2>
                {loading && (
                    <p>Loading requests...</p>
                )}
                {!loading && bookings.length === 0 && (
                    <p>No pending bookings.</p>
                )}
                <div className="row g-3">
                    {bookings.map((booking) => (
                        <div 
                            className="col-md-6"
                            key={booking.bookId}
                        >
                            <div className="card shadow">
                                <div className="card-body">
                                    <h5>
                                        Customer
                                    </h5>
                                    <p>
                                        {booking.user.firstName}{" "}
                                        {booking.user.lastName}
                                    </p>
                                    <h5>
                                        Car
                                    </h5>
                                    <p>
                                        {booking.car.carBrand}{" "}
                                        {booking.car.carName}
                                    </p>
                                    <p>
                                        <strong>
                                            Start:
                                        </strong>{" "}
                                        {booking.bookingDateStart}
                                    </p>
                                    <p>
                                        <strong>
                                            End:
                                        </strong>{" "}
                                        {booking.bookingDateEnd}
                                    </p>
                                    <p>
                                        <strong>
                                            Status:
                                        </strong>{" "}
                                        {booking.status}
                                    </p>
                                    <button
                                        className="btn btn-success me-2"
                                        onClick={() =>
                                            updateStatus(
                                                booking.bookId,
                                                "CONFIRMED"
                                            )
                                        }
                                    >
                                        Confirm
                                    </button>
                                    <button
                                        className="btn btn-danger"
                                        onClick={() =>
                                            updateStatus(
                                                booking.bookId,
                                                "REJECTED"
                                            )
                                        }
                                    >
                                        Reject
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </>
    );
}