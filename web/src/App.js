import './App.css';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import { useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from "./layout/Navbar";
import Cars from './page/Cars';
import CarDetails from './page/CarDetails';
import Login from './page/Login';
import Register from './page/Register';
import MyBookings from './page/MyBookings';
import MyCars from './page/MyCars';
import AddCar from './page/AddCar';

function App() {
  // keeping the logged in user simple, just save it in localStorage
  // so it doesnt disappear when the page refreshes
  const savedUser = localStorage.getItem('user');
  const [user, setUser] = useState(savedUser ? JSON.parse(savedUser) : null);

  function handleLogin(userData) {
    setUser(userData);
    localStorage.setItem('user', JSON.stringify(userData));
  }

  function handleLogout() {
    setUser(null);
    localStorage.removeItem('user');
  }

  return (
    <BrowserRouter>
      <div className="App">
        <Navbar user={user} handleLogout={handleLogout} />
        <Routes>
          <Route path="/" element={<Navigate to="/cars" />} />
          <Route path="/cars" element={<Cars />} />
          <Route path="/cars/:carId" element={<CarDetails user={user} />} />
          <Route path="/login" element={<Login handleLogin={handleLogin} />} />
          <Route path="/register" element={<Register />} />
          <Route path="/my-bookings" element={<MyBookings user={user} />} />
          <Route path="/my-cars" element={<MyCars user={user} />} />
          <Route path="/my-cars/new" element={<AddCar user={user} />} />

        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
