import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import API_URL from "../../apiUrl";
import AdminNavbar from "./AdminNavbar";

export default function CarManagement({user, handleLogout}) {
    const navigate = useNavigate();
    const [cars, setCars] = useState([]);
    function loadCars(){
        fetch(API_URL + "/cars")
        .then((res)=>res.json())
        .then((data)=>{
            setCars(data);
        })
        .catch((err)=>{
            console.log(err);
        });

    }

    useEffect(()=>{
        loadCars();
    },[]);

    function deleteCar(id){
        if(!window.confirm("Delete this car?")){
            return;
        }
        fetch(API_URL + "/cars/" + id,{
            method:"DELETE"
        })
        .then(()=>{
            alert("Car deleted");
            loadCars();
        })
        .catch((err)=>{

            console.log(err);
        });
    }


    return (
        <>
        <AdminNavbar
            user={user}
            handleLogout={handleLogout}
        />
        <div className="container mt-4">
             <div className="d-flex justify-content-between align-items-center mb-4">
                <h2>Car Management</h2>
                <button
                    className="btn btn-success"
                    onClick={() => navigate("/admin/cars/new")}
                >
                    + Add Car
                </button>
        </div>
            <div className="row g-3">
            {cars.map((car)=>(
                <div 
                    className="col-md-4"
                    key={car.carId}
                >
                    <div className="card shadow">
                        <div className="card-body">
                            <h5>
                                {car.carBrand} {car.carName}
                            </h5>
                            <p>
                                Plate: {car.plateNumber}
                            </p>
                            <p>
                                Price: ${car.carPrice}
                            </p>
                            <button
                                className="btn btn-danger"
                                onClick={()=>
                                    deleteCar(car.carId)
                                }
                            >
                                Delete
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