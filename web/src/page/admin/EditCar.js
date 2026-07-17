import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import API_URL from "../../apiUrl";
import AdminNavbar from "./AdminNavbar";


export default function EditCar({user, handleLogout}) {

    const { carId } = useParams();
    const navigate = useNavigate();


    const [brand, setBrand] = useState("");
    const [model, setModel] = useState("");
    const [year, setYear] = useState("");
    const [details, setDetails] = useState("");
    const [pricePerDay, setPricePerDay] = useState("");
    const [plateNumber, setPlateNumber] = useState("");
    const [imageUrl, setImageUrl] = useState("");

    const [error, setError] = useState("");



    useEffect(() => {

        fetch(API_URL + "/cars/" + carId)

        .then(res => res.json())

        .then(car => {

            setBrand(car.brand);
            setModel(car.model);
            setYear(car.year);
            setDetails(car.details);
            setPricePerDay(car.pricePerDay);
            setPlateNumber(car.plateNumber);
            setImageUrl(car.imageUrl || "");

        });

    }, [carId]);




    function submitForm(e){

        e.preventDefault();

        fetch(API_URL + "/cars/" + carId, {

            method:"PUT",

            headers:{
                "Content-Type":"application/json"
            },

            body: JSON.stringify({

                brand,
                model,
                year:Number(year),
                details,
                pricePerDay:Number(pricePerDay),
                plateNumber,
                imageUrl

            })

        })

        .then(res=>{

            if(!res.ok){
                throw new Error("Failed updating car");
            }

            return res.json();

        })

        .then(()=>{

            alert("Car updated successfully");

            navigate("/admin/cars");

        })

        .catch(err=>{

            setError(err.message);

        });

    }



    return (

        <>

        <AdminNavbar
            user={user}
            handleLogout={handleLogout}
        />


        <div className="container py-4" style={{maxWidth:500}}>

            <h2>Edit Car</h2>


            {error &&
                <div className="alert alert-danger">
                    {error}
                </div>
            }


            <form onSubmit={submitForm}>


                <input
                    className="form-control mb-3"
                    placeholder="Brand"
                    value={brand}
                    onChange={e=>setBrand(e.target.value)}
                />


                <input
                    className="form-control mb-3"
                    placeholder="Model"
                    value={model}
                    onChange={e=>setModel(e.target.value)}
                />


                <input
                    className="form-control mb-3"
                    type="number"
                    placeholder="Year"
                    value={year}
                    onChange={e=>setYear(e.target.value)}
                />


                <textarea
                    className="form-control mb-3"
                    placeholder="Details"
                    value={details}
                    onChange={e=>setDetails(e.target.value)}
                />


                <input
                    className="form-control mb-3"
                    type="number"
                    step="0.01"
                    placeholder="Price per day"
                    value={pricePerDay}
                    onChange={e=>setPricePerDay(e.target.value)}
                />


                <input
                    className="form-control mb-3"
                    placeholder="Plate Number"
                    value={plateNumber}
                    onChange={e=>setPlateNumber(e.target.value)}
                />


                <input
                    className="form-control mb-3"
                    placeholder="Image URL"
                    value={imageUrl}
                    onChange={e=>setImageUrl(e.target.value)}
                />


                <button className="btn btn-primary w-100">
                    Save Changes
                </button>


            </form>

        </div>

        </>

    );

}