import './App.css';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import { useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate, useLocation } from 'react-router-dom';

import Navbar from "./layout/Navbar";
import Cars from './page/cars/Cars';
import CarDetails from './page/cars/CarDetails';
import Login from './page/auth/Login';
import Register from './page/auth/Register';
import MyBookings from './page/booking/MyBookings';
import CarManagement from './page/admin/CarManagement';

import MyCars from './page/admin/MyCars';
import AddCar from './page/admin/AddCar';
import AdminDashboard from './page/admin/AdminDashboard';
import ProfileManagement from './page/admin/ProfileManagement';
import BookingRequest from './page/admin/BookingRequests';

function AppContent({ user, handleLogin, handleLogout }) {

  const location = useLocation();

  const isAdminPage = location.pathname.startsWith("/admin");


  return (
    <div className="App">

      {!isAdminPage && (
        <Navbar
          user={user}
          handleLogout={handleLogout}
        />
      )}

      <Routes>

        <Route 
          path="/" 
          element={<Navigate to="/cars" />} 
        />

        <Route 
          path="/cars" 
          element={<Cars />} 
        />

        <Route 
          path="/cars/:carId" 
          element={<CarDetails user={user} />} 
        />

        <Route 
          path="/login" 
          element={<Login handleLogin={handleLogin} />} 
        />

        <Route 
          path="/register" 
          element={<Register />} 
        />

        <Route 
          path="/my-bookings" 
          element={<MyBookings user={user} />} 
        />


        <Route 
          path="/my-cars" 
          element={<MyCars user={user} />} 
        />


        <Route 
          path="/my-cars/new" 
          element={<AddCar user={user} />} 
        />


        {/* ADMIN DASHBOARD */}
        <Route 
          path="/admin" 
          element={
            user?.role === "ADMIN"
            ?
            <AdminDashboard 
              user={user}
              handleLogout={handleLogout}
            />
            :
            <Navigate to="/cars"/>
          }
        />


        {/* ADMIN PROFILE */}
        <Route 
          path="/admin/profile" 
          element={
            user?.role === "ADMIN"
            ?
            <ProfileManagement 
              user={user}
               handleLogout={handleLogout}
            />
            :
            <Navigate to="/cars"/>
          }
        />


        {/* ADMIN CAR MANAGEMENT */}
        <Route 
          path="/admin/cars" 
          element={
            user?.role === "ADMIN"
            ?
            <CarManagement
              user={user}
              handleLogout={handleLogout}
            />
            :
            <Navigate to="/cars"/>
          }
        />

        {/* ADMIN ADD CAR */}
        <Route 
          path="/admin/cars/new" 
          element={
            user?.role === "ADMIN"
            ?
            <AddCar
              user={user}
              handleLogout={handleLogout}
            />
            :
            <Navigate to="/cars"/>
          }
        />


        {/* ADMIN BOOKING REQUESTS */}
        <Route 
          path="/admin/bookings" 
          element={
            user?.role === "ADMIN"
            ?
            <BookingRequest user={user}/>
            :
            <Navigate to="/cars"/>
          }
        />

      </Routes>

    </div>
  );
}



function App() {

  const savedUser = localStorage.getItem('user');

  const [user, setUser] = useState(
    savedUser ? JSON.parse(savedUser) : null
  );


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
      <AppContent 
        user={user}
        handleLogin={handleLogin}
        handleLogout={handleLogout}
      />
    </BrowserRouter>
  );
}


export default App;