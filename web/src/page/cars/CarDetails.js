import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import API_URL from '../../apiUrl';

export default function CarDetails(props) {
  const { carId } = useParams();
  const navigate = useNavigate();
  const user = props.user;

  const [car, setCar] = useState(null);
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [error, setError] = useState('');

  useEffect(() => {
    fetch(API_URL + "/cars/" + carId)
      .then((res) => res.json())
      .then((data) => setCar(data))
      .catch((err) => console.log(err));
  }, [carId]);

  let days = 0;
  if (startDate && endDate) {
    const start = new Date(startDate);
    const end = new Date(endDate);

    days = Math.round((end - start) / (1000 * 60 * 60 * 24));

    if (days < 1) days = 0;
  }

  const total = car ? days * car.carPrice : 0;

  function bookCar(e) {
    e.preventDefault();
    setError('');

    if (!user) {
      navigate('/login');
      return;
    }

    if (days <= 0) {
      setError('Pick a valid start and end date first');
      return;
    }

    fetch(API_URL + "/bookings", {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    customerId: user.userId,
    carId: car.carId,
    startDate: startDate,
    endDate: endDate
  })
})  
      .then((res) => {
        if (!res.ok) {
          return res.json().then((data) => {
            throw new Error(data.message);
          });
        }

        return res.json();
      })
      .then(() => {
        alert("Booked Successfully");
        navigate('/cars');
      })
      .catch((err) => {
        setError(err.message);
      });
  }

  if (!car) {
    return <div className="container py-4">Loading...</div>;
  }

  return (
    <div className="container py-4">
      <div className="row g-4">
        <div className="col-md-6">
          <h2>{car.carBrand} {car.carName}</h2>

          <ul className="list-group mb-3">
            <li className="list-group-item">
              <strong>Owner:</strong> {car.carOwner}
            </li>

            <li className="list-group-item">
              <strong>Details:</strong> {car.carDetails}
            </li>

            <li className="list-group-item">
              <strong>Price per day:</strong> ${car.carPrice}
            </li>
          </ul>
        </div>

        <div className="col-md-6">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">Book this car</h5>

              {error && (
                <div className="alert alert-danger">
                  {error}
                </div>
              )}

              <form onSubmit={bookCar}>
                <div className="mb-3">
                  <label className="form-label">Start Date</label>
                  <input
                    type="date"
                    className="form-control"
                    value={startDate}
                    onChange={(e) => setStartDate(e.target.value)}
                    required
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label">End Date</label>
                  <input
                    type="date"
                    className="form-control"
                    value={endDate}
                    onChange={(e) => setEndDate(e.target.value)}
                    required
                  />
                </div>

                {days > 0 && (
                  <p>
                    {days} day(s) × ${car.carPrice} = <b>${total}</b>
                  </p>
                )}

                <button className="btn btn-primary w-100" type="submit">
                  Confirm Booking
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
