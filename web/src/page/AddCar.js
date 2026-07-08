import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import API_URL from '../apiUrl';

export default function AddCar(props) {
  const user = props.user;
  const navigate = useNavigate();

  const [brand, setBrand] = useState('');
  const [model, setModel] = useState('');
  const [year, setYear] = useState('');
  const [color, setColor] = useState('');
  const [plateNumber, setPlateNumber] = useState('');
  const [pricePerDay, setPricePerDay] = useState('');
  const [availability, setAvailability] = useState(true);
  const [error, setError] = useState('');

  function submitForm(e) {
    e.preventDefault();
    setError('');

    fetch(API_URL + "/cars", {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        ownerId: user.userId,
        brand: brand,
        model: model,
        year: Number(year),
        color: color,
        plateNumber: plateNumber,
        pricePerDay: Number(pricePerDay),
        availability: availability
      })
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error("Something went wrong adding the car");
        }
        return res.json();
      })
      .then(() => {
        navigate('/my-cars');
      })
      .catch((err) => {
        setError(err.message);
      });
  }

  if (!user) {
    return <div className="container py-4"><p>Please log in first.</p></div>;
  }

  return (
    <div className="container py-4" style={{ maxWidth: 500 }}>
      <h2 className="mb-3">List a New Car</h2>

      {error && <div className="alert alert-danger">{error}</div>}

      <form onSubmit={submitForm}>
        <div className="row">
          <div className="col mb-3">
            <label className="form-label">Brand</label>
            <input className="form-control" value={brand} onChange={(e) => setBrand(e.target.value)} required />
          </div>
          <div className="col mb-3">
            <label className="form-label">Model</label>
            <input className="form-control" value={model} onChange={(e) => setModel(e.target.value)} required />
          </div>
        </div>

        <div className="row">
          <div className="col mb-3">
            <label className="form-label">Year</label>
            <input type="number" className="form-control" value={year} onChange={(e) => setYear(e.target.value)} required />
          </div>
          <div className="col mb-3">
            <label className="form-label">Color</label>
            <input className="form-control" value={color} onChange={(e) => setColor(e.target.value)} required />
          </div>
        </div>

        <div className="mb-3">
          <label className="form-label">Plate Number</label>
          <input className="form-control" value={plateNumber} onChange={(e) => setPlateNumber(e.target.value)} required />
        </div>

        <div className="mb-3">
          <label className="form-label">Price per Day ($)</label>
          <input type="number" step="0.01" className="form-control" value={pricePerDay} onChange={(e) => setPricePerDay(e.target.value)} required />
        </div>

        <div className="form-check mb-3">
          <input
            type="checkbox"
            className="form-check-input"
            id="availabilityCheck"
            checked={availability}
            onChange={(e) => setAvailability(e.target.checked)}
          />
          <label className="form-check-label" htmlFor="availabilityCheck">
            Available for booking immediately
          </label>
        </div>

        <button className="btn btn-primary w-100" type="submit">Add Car</button>
      </form>
    </div>
  );
}
