package cit.edu.inosanto.backend.CarGo.features.bookings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cit.edu.inosanto.backend.CarGo.features.bookings.entity.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserUserId(Long userId);

    List<Booking> findByStatus(String status);

}