import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import API_URL from '../apiUrl';

export default function MyCars(props) {
  const user = props.user;
  const [cars, setCars] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (user) {
      loadCars();
    }
    // eslint-disable-next-line
  }, [user]);

  function loadCars() {
    setLoading(true);
    fetch(API_URL + "/cars/owner/" + user.userId)
      .then((res) => res.json())
      .then((data) => {
        setCars(data);
        setLoading(false);
      })
      .catch((err) => {
        console.log(err);
        setLoading(false);
      });
  }

  function toggleAvailable(car) {
    fetch(API_URL + "/cars/" + car.carId + "/availability", {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ availability: !car.availability })
    })
      .then(() => loadCars())
      .catch((err) => console.log(err));
  }

  function deleteCar(carId) {
    const sure = window.confirm("Remove this car?");
    if (!sure) return;

    fetch(API_URL + "/cars/" + carId, { method: 'DELETE' })
      .then(() => loadCars())
      .catch((err) => console.log(err));
  }

  if (!user) {
    return <div className="container py-4"><p>Please log in first.</p></div>;
  }

  return (
    <div className="container py-4">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>My Cars</h2>
        <Link to="/my-cars/new" className="btn btn-primary">+ Add Car</Link>
      </div>

      {loading && <p>Loading...</p>}

      {!loading && cars.length === 0 && <p>You haven't listed any cars yet.</p>}

      {!loading && cars.length > 0 && (
        <table className="table align-middle">
          <thead>
            <tr>
              <th>Car</th>
              <th>Plate</th>
              <th>Price/day</th>
              <th>Available</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {cars.map((car) => (
              <tr key={car.carId}>
                <td>{car.brand} {car.model} ({car.year})</td>
                <td>{car.plateNumber}</td>
                <td>${car.pricePerDay}</td>
                <td>
                  <input
                    type="checkbox"
                    checked={car.availability}
                    onChange={() => toggleAvailable(car)}
                  />
                </td>
                <td className="text-end">
                  <button className="btn btn-sm btn-outline-danger" onClick={() => deleteCar(car.carId)}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
