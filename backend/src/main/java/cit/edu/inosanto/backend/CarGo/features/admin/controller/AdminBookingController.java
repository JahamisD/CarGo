package cit.edu.inosanto.backend.CarGo.features.admin.controller;

import cit.edu.inosanto.backend.CarGo.features.bookings.entity.Booking;
import cit.edu.inosanto.backend.CarGo.features.bookings.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/bookings")
public class AdminBookingController {

    @Autowired
    private BookingRepository bookingRepository;


    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }


    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveBooking(@PathVariable Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElse(null);

        if (booking == null) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("message", "Booking not found"));
        }

        booking.setStatus("CONFIRMED");

        bookingRepository.save(booking);

        return ResponseEntity.ok(
                Map.of("message", "Booking confirmed")
        );
    }


    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectBooking(@PathVariable Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElse(null);

        if (booking == null) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("message", "Booking not found"));
        }

        booking.setStatus("CANCELLED");

        bookingRepository.save(booking);

        return ResponseEntity.ok(
                Map.of("message", "Booking cancelled")
        );
    }
}