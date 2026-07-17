package cit.edu.inosanto.backend.CarGo.features.bookings.controller;

import cit.edu.inosanto.backend.CarGo.features.bookings.dto.BookingRequest;
import cit.edu.inosanto.backend.CarGo.features.bookings.repository.BookingRepository;
import cit.edu.inosanto.backend.CarGo.features.bookings.entity.Booking;
import cit.edu.inosanto.backend.CarGo.features.users.entity.User;
import cit.edu.inosanto.backend.CarGo.features.users.repository.UserRepository;
import cit.edu.inosanto.backend.CarGo.features.cars.entity.Cars;
import cit.edu.inosanto.backend.CarGo.features.cars.repository.CarRepository;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @PostMapping
    public Booking newBooking(@RequestBody BookingRequest request) {

        User user = userRepository.findById(request.getCustomerId()).orElse(null);
        Cars car = carRepository.findById(request.getCarId()).orElse(null);

        Booking booking = new Booking();

        booking.setUser(user);
        booking.setCar(car);
        booking.setBookingDateStart(request.getStartDate());
        booking.setBookingDateEnd(request.getEndDate());
        booking.setStatus("PENDING");

        return bookingRepository.save(booking);
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getUserBookings(@PathVariable Long userId) {
        return bookingRepository.findByUserUserId(userId);
    }

    @GetMapping("/pending")
    public List<Booking> getPendingBookings() {

        return bookingRepository.findByStatus("PENDING");

    }


    @PutMapping("/{id}/status")
    public Booking updateBookingStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request
    ) {

        Booking booking = bookingRepository.findById(id).orElse(null);

        if (booking == null) {
            return null;
        }


        booking.setStatus(request.get("status"));

        return bookingRepository.save(booking);

    }
}