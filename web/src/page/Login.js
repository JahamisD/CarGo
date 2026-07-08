import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import API_URL from '../apiUrl';

export default function Login(props) {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  function submitForm(e) {
    e.preventDefault();
    setError('');

    fetch(API_URL + "/auth/login", {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email: email, password: password })
    })
      .then(async (res) => {
        const data = await res.json();

       if (!res.ok) {
            throw new Error(data.message || "Wrong email or password");
        }
        return data;
      })
      .then((data) => {
        props.handleLogin(data.user);
        navigate('/cars');
      })
      .catch((err) => {
        setError(err.message);
      });
  }

  return (
    <div className="container py-4" style={{ maxWidth: 420 }}>
      <h2 className="mb-3">Log In</h2>

      {error && <div className="alert alert-danger">{error}</div>}

      <form onSubmit={submitForm}>
        <div className="mb-3">
          <label className="form-label">Email</label>
          <input
            type="email"
            className="form-control"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Password</label>
          <input
            type="password"
            className="form-control"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>

        <button className="btn btn-primary w-100" type="submit">Log In</button>
      </form>

      <p className="mt-3">
        Don't have an account? <Link to="/register">Register</Link>
      </p>
    </div>
  );
}
