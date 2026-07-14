package cit.edu.inosanto.backend.CarGo.features.bookings.service;

import cit.edu.inosanto.backend.CarGo.features.bookings.repository.BookingRepository;

public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

}
