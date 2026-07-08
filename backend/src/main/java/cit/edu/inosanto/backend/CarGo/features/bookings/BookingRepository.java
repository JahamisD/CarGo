package cit.edu.inosanto.backend.CarGo.features.bookings;

import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;
import cit.edu.inosanto.backend.CarGo.features.bookings.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}