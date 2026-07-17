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

                <h2>
                    Car Management
                </h2>


                <button
                    className="btn btn-success"
                    onClick={()=>navigate("/admin/cars/new")}
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

                    <div 
                        className="card shadow h-100"
                        style={{ minHeight: "380px" }}
                    >


                        {car.imageUrl && (

                            <img
                                src={car.imageUrl}
                                className="card-img-top"
                                alt={car.model} 
                                style={{
                                    height:"200px",
                                    objectFit:"cover"
                                }}
                            />

                        )}



                        <div 
                            className="card-body d-flex flex-column"
                        >


                            <h5>
                                {car.brand} {car.model}
                            </h5>


                            <p>
                                Year: {car.year}
                            </p>


                            <p>
                                Details:
                                <br/>
                                {car.details}
                            </p>


                            <p>
                                Plate:
                                {car.plateNumber}
                            </p>


                            <p>
                                Price:
                                ₱{car.pricePerDay}/day
                            </p>



                            <div className="d-flex gap-2 mt-auto">

                                <button
                                    className="btn btn-warning"
                                    onClick={() => navigate("/admin/cars/edit/" + car.carId)}
                                >
                                    Edit
                                </button>


                                <button
                                    className="btn btn-danger"
                                    onClick={()=>deleteCar(car.carId)}
                                >
                                    Delete
                                </button>

                            </div>


                        </div>


                    </div>

                </div>

            ))}


            </div>


        </div>

        </>
    );

}