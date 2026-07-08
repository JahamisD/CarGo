package cit.edu.inosanto.backend.CarGo.features.bookings;

import cit.edu.inosanto.backend.CarGo.features.bookings.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cit.edu.inosanto.backend.CarGo.features.bookings.BookingRepository;

@RestController
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/bookings")
    Booking newUser(@RequestBody Booking newBooking) {
        return bookingRepository.save(newBooking);
    }
}