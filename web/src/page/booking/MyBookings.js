import React, { useEffect, useState } from "react";
import API_URL from "../../apiUrl";

export default function MyBookings({ user }) {

    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);


    function loadBookings() {

        setLoading(true);

        fetch(API_URL + "/bookings/user/" + user.userId)
            .then((res) => res.json())
            .then((data) => {

                console.log("My bookings:", data);

                setBookings(Array.isArray(data) ? data : []);

                setLoading(false);
            })
            .catch((err) => {

                console.log(err);

                setLoading(false);
            });
    }


    useEffect(() => {

        if (user) {
            loadBookings();
        } else {
            setLoading(false);
        }

    }, [user]);


    function calculateTotalDays(booking) {

        const start = new Date(booking.bookingDateStart);
        const end = new Date(booking.bookingDateEnd);

        return Math.ceil(
            (end - start) / (1000 * 60 * 60 * 24)
        );
    }


    if (!user) {

        return (
            <div className="container py-4">
                <h3>Please login first</h3>
            </div>
        );
    }


    return (

        <div className="container py-4">

            <h2>
                My Bookings
            </h2>


            {loading && (
                <p>
                    Loading bookings...
                </p>
            )}


            {!loading && bookings.length === 0 && (

                <p>
                    No bookings found.
                </p>

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
                                    Car
                                </h5>

                                <p>
                                    {booking.car?.brand}{" "}
                                    {booking.car?.model}
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
                                        Rental Duration:
                                    </strong>{" "}
                                    {calculateTotalDays(booking)} day
                                    {calculateTotalDays(booking) > 1 ? "s" : ""}
                                </p>


                                <p>
                                    <strong>
                                        Price Per Day:
                                    </strong>{" "}
                                    ₱{booking.car?.pricePerDay ?? 0}
                                </p>


                                <p>
                                    <strong>
                                        Total Price:
                                    </strong>{" "}
                                    ₱{booking.totalPrice ?? 0}
                                </p>


                                <p>
                                    <strong>
                                        Status:
                                    </strong>{" "}
                                    {booking.status}
                                </p>


                            </div>

                        </div>

                    </div>

                ))}

            </div>

        </div>

    );
}