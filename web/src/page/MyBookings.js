import React, { useEffect, useState } from 'react';
import API_URL from '../apiUrl';

export default function MyBookings({ user }) {

  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {

    console.log("User inside MyBookings:", user);

    if (!user) {
        return;
    }

    fetch(API_URL + "/bookings/user/" + user.userId)

    fetch(API_URL + "/bookings/user/" + user.userId)
  .then((res) => res.json())
  .then((data) => {
    console.log(data);

    setBookings(Array.isArray(data) ? data : []);

    setLoading(false);
  })
  .catch((err) => {
    console.log(err);
    setLoading(false);
  });

  }, [user]);


  if (!user) {
    return (
      <div className="container py-4">
        <h3>Please login first</h3>
      </div>
    );
  }


  return (
    <div className="container py-4">

      <h2>My Bookings</h2>

      {loading && <p>Loading bookings...</p>}


      {!loading && bookings.length === 0 && (
        <p>No bookings found.</p>
      )}


      <div className="row g-3">

        {bookings.map((booking) => (

          <div className="col-md-6" key={booking.bookId}>

            <div className="card shadow-sm">

              <div className="card-body">

                <h5>
                  {booking.car.carBrand} {booking.car.carName}
                </h5>

                <p>
                  <strong>Start:</strong>{" "}
                  {booking.bookingDateStart}
                </p>

                <p>
                  <strong>End:</strong>{" "}
                  {booking.bookingDateEnd}
                </p>

                <p>
                  <strong>Status:</strong>{" "}
                  {booking.status}
                </p>

                <p>
                  <strong>Price:</strong>{" "}
                  ${booking.car.carPrice}/day
                </p>

              </div>

            </div>

          </div>

        ))}

      </div>

    </div>
  );
}