import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import API_URL from '../../apiUrl';

export default function Cars() {
  const [cars, setCars] = useState([]);
  const [loading, setLoading] = useState(true);
  const [brand, setBrand] = useState('');

  useEffect(() => {
    fetchCars();
  }, []);

  function fetchCars() {
    setLoading(true);

    let url = API_URL + "/cars";

    fetch(url)
      .then((res) => res.json())
      .then((data) => {
        if (brand) {
          const filtered = data.filter((car) =>
            car.brand.toLowerCase().includes(brand.toLowerCase())
          );
          setCars(filtered);
        } else {
          setCars(data);
        }

        setLoading(false);
      })
      .catch((err) => {
        console.log(err);
        setLoading(false);
      });
  }

  function searchSubmit(e) {
    e.preventDefault();
    fetchCars();
  }

  return (
    <div className="container py-4">

      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Available Cars</h2>

        <form className="d-flex" onSubmit={searchSubmit}>
          <input
            className="form-control me-2"
            placeholder="Search by brand..."
            value={brand}
            onChange={(e) => setBrand(e.target.value)}
          />

          <button className="btn btn-primary" type="submit">
            Search
          </button>
        </form>
      </div>


      {loading && <p>Loading cars...</p>}

      {!loading && cars.length === 0 && (
        <p>No cars found.</p>
      )}


      <div className="row g-3">

        {cars.map((car) => (

          <div 
            className="col-12 col-md-6 col-lg-4"
            key={car.carId}
          >

            <div className="card h-100 shadow-sm">

              {car.imageUrl && (
                <img
                  src={car.imageUrl}
                  className="card-img-top"
                  alt={car.model}
                  style={{
                    height: "200px",
                    objectFit: "cover"
                  }}
                />
              )}


              <div className="card-body">

                <h5 className="card-title">
                  {car.brand} {car.model}
                </h5>


                <p className="card-text">
                  Year: {car.year}
                </p>


                <p className="card-text">
                  {car.details}
                </p>


                <p className="card-text fw-bold">
                  ${car.pricePerDay} / day
                </p>


                <Link
                  to={"/cars/" + car.carId}
                  className="btn btn-outline-primary btn-sm"
                >
                  View Details
                </Link>

              </div>

            </div>

          </div>

        ))}

      </div>

    </div>
  );
}