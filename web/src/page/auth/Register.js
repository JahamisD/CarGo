import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import API_URL from '../../apiUrl';

export default function Register() {
  const navigate = useNavigate();

  // just using separate states instead of one object, easier to follow
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  function submitForm(e) {
    e.preventDefault();
    setError('');

    fetch(API_URL + "/auth/register", {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password
      })
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error("Could not create account, maybe email is taken already");
        }
        return res.json();
      })
      .then(() => {
        navigate('/login');
      })
      .catch((err) => {
        setError(err.message);
      });
  }

  return (
    <div className="container py-4" style={{ maxWidth: 420 }}>
      <h2 className="mb-3">Create Account</h2>

      {error && <div className="alert alert-danger">{error}</div>}

      <form onSubmit={submitForm}>
        <div className="row">
          <div className="col mb-3">
            <label className="form-label">First Name</label>
            <input className="form-control" value={firstName} onChange={(e) => setFirstName(e.target.value)} required />
          </div>
          <div className="col mb-3">
            <label className="form-label">Last Name</label>
            <input className="form-control" value={lastName} onChange={(e) => setLastName(e.target.value)} required />
          </div>
        </div>

        <div className="mb-3">
          <label className="form-label">Email</label>
          <input type="email" className="form-control" value={email} onChange={(e) => setEmail(e.target.value)} required />
        </div>

        <div className="mb-3">
          <label className="form-label">Password</label>
          <input type="password" className="form-control" value={password} onChange={(e) => setPassword(e.target.value)} required />
        </div>

        <button className="btn btn-primary w-100" type="submit">Register</button>
      </form>

      <p className="mt-3">
        Already have an account? <Link to="/login">Log in</Link>
      </p>
    </div>
  );
}
