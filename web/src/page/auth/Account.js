import { useNavigate } from "react-router-dom";
import React, { useEffect, useState } from "react";
import API_URL from "../../apiUrl";

export default function Account({ user }) {
    const navigate = useNavigate();
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");

    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch(API_URL + "/users/" + user.userId)
            .then(res => res.json())
            .then(data => {
                setFirstName(data.firstName);
                setLastName(data.lastName);
                setEmail(data.email);
                setLoading(false);
            });
    }, [user.userId]);

    function saveProfile(e) {
        e.preventDefault();

        fetch(API_URL + "/users/" + user.userId, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                firstName,
                lastName,
                email
            })
        })
            .then(res => res.json())
            .then(updatedUser => {

                localStorage.setItem(
                    "user",
                    JSON.stringify(updatedUser)
                );

                alert("Profile updated successfully!");
            });
    }

    if (loading) {
        return (
            <div className="container mt-4">
                Loading...
            </div>
        );
    }

    return (

        <div className="container mt-4">

            <h2>My Account</h2>

            <form onSubmit={saveProfile}>

                <div className="mb-3">
                    <label className="form-label">
                        First Name
                    </label>

                    <input
                        className="form-control"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                    />
                </div>

                <div className="mb-3">

                    <label className="form-label">
                        Last Name
                    </label>
                    <input
                        className="form-control"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                    />
                </div>

                <div className="mb-3">

                    <label className="form-label">
                        Email
                    </label>
                    <input
                        className="form-control"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />

                </div>

                <div className="mb-3">

                    <label className="form-label">
                        Role
                    </label>

                    <input
                        className="form-control"
                        value={user.role}
                        disabled
                    />

                </div>

                <button className="btn btn-primary">
                    Save Changes
                </button>

            </form>

        </div>

    );

}