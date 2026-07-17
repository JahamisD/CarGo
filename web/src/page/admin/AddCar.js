import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import API_URL from '../../apiUrl';
import AdminNavbar from './AdminNavbar';

export default function AddCar({user, handleLogout}) {

  const navigate = useNavigate();

  const [brand, setBrand] = useState('');
  const [model, setModel] = useState('');
  const [year, setYear] = useState('');
  const [details, setDetails] = useState('');
  const [plateNumber, setPlateNumber] = useState('');
  const [pricePerDay, setPricePerDay] = useState('');
  const [error, setError] = useState('');


  function submitForm(e) {
    e.preventDefault();
    setError('');

    fetch(API_URL + "/cars", {
      method: 'POST',
      headers: { 
        'Content-Type': 'application/json' 
      },
      body: JSON.stringify({
        brand: brand,
        model: model,
        year: Number(year),
        details: details,
        plateNumber: plateNumber,
        pricePerDay: Number(pricePerDay)
      })
    })
    .then((res) => {
      if (!res.ok) {
        throw new Error("Something went wrong adding the car");
      }

      return res.json();
    })
    .then(() => {
      alert("Car added successfully!");
      navigate('/admin/cars');
    })
    .catch((err) => {
      setError(err.message);
    });
  }


  if (!user || user.role !== "ADMIN") {
    return (
      <div className="container py-4">
        <p>You do not have permission to access this page.</p>
      </div>
    );
  }


  return (
    <>
      <AdminNavbar 
        user={user}
        handleLogout={handleLogout}
      />

      <div className="container py-4" style={{ maxWidth: 500 }}>

        <h2 className="mb-3">
          List a New Car
        </h2>


        {error && (
          <div className="alert alert-danger">
            {error}
          </div>
        )}


        <form onSubmit={submitForm}>


          <div className="row">

            <div className="col mb-3">
              <label className="form-label">
                Brand
              </label>

              <input
                className="form-control"
                value={brand}
                onChange={(e)=>setBrand(e.target.value)}
                required
              />
            </div>


            <div className="col mb-3">
              <label className="form-label">
                Model
              </label>

              <input
                className="form-control"
                value={model}
                onChange={(e)=>setModel(e.target.value)}
                required
              />
            </div>

          </div>



          <div className="mb-3">

            <label className="form-label">
              Year
            </label>

            <input
              type="number"
              className="form-control"
              value={year}
              onChange={(e)=>setYear(e.target.value)}
              required
            />

          </div>



          <div className="mb-3">

            <label className="form-label">
              Details
            </label>

            <textarea
              className="form-control"
              value={details}
              onChange={(e)=>setDetails(e.target.value)}
              rows="3"
              required
            />

          </div>



          <div className="mb-3">

            <label className="form-label">
              Plate Number
            </label>

            <input
              className="form-control"
              value={plateNumber}
              onChange={(e)=>setPlateNumber(e.target.value)}
              required
            />

          </div>



          <div className="mb-3">

            <label className="form-label">
              Price per Day
            </label>

            <input
              type="number"
              step="0.01"
              className="form-control"
              value={pricePerDay}
              onChange={(e)=>setPricePerDay(e.target.value)}
              required
            />

          </div>



          <button 
            className="btn btn-primary w-100"
            type="submit"
          >
            Add Car
          </button>


        </form>

      </div>
    </>
  );
}