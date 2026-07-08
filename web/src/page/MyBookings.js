import React, { useEffect, useState } from 'react';
import API_URL from '../apiUrl';

export default function MyBookings(props) {
  const user = props.user;
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (user) {
      loadBookings();
    }
    // eslint-disable-next-line
  }, [user]);

  function loadBookings() {
    setLoading(true);
    fetch(API_URL + "/bookings/customer/" + user.userId)
      .then((res) => res.json())
      .then((data) => {
        setBookings(data);
        setLoading(false);
      })
      .catch((err) => {
        console.log(err);
        setLoading(false);
      });
  }

  function cancelBooking(bookingId) {
    fetch(API_URL + "/bookings/" + bookingId + "/status", {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ bookingStatus: 'CANCELLED' })
    })
      .then(() => {
        loadBookings(); // just refresh the list after cancelling
      })
      .catch((err) => console.log(err));
  }

  if (!user) {
    return <div className="container py-4"><p>Please log in first.</p></div>;
  }

  return (
    <div className="container py-4">
      <h2 className="mb-3">My Bookings</h2>

      {loading && <p>Loading...</p>}

      {!loading && bookings.length === 0 && <p>You have no bookings yet.</p>}

      {!loading && bookings.length > 0 && (
        <table className="table">
          <thead>
            <tr>
              <th>Car</th>
              <th>Start Date</th>
              <th>End Date</th>
              <th>Status</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {bookings.map((b) => (
              <tr key={b.bookingId}>
                <td>{b.car.brand} {b.car.model}</td>
                <td>{b.startDate}</td>
                <td>{b.endDate}</td>
                <td><span className="badge bg-secondary">{b.bookingStatus}</span></td>
                <td>
                  {(b.bookingStatus === 'PENDING' || b.bookingStatus === 'CONFIRMED') && (
                    <button className="btn btn-sm btn-outline-danger" onClick={() => cancelBooking(b.bookingId)}>
                      Cancel
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
