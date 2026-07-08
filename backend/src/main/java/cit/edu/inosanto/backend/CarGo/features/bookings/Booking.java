package cit.edu.inosanto.backend.CarGo.features.bookings;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table( name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private LocalDate bookingDateStart;
    private LocalDate bookingDateEnd;
    private String payment; // dropdown
    private String status; // pending, cancelled, confirmed

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getBookingDateStart() {
        return bookingDateStart;
    }

    public void setBookingDateStart(LocalDate bookingDateStart) {
        this.bookingDateStart = bookingDateStart;
    }

    public LocalDate getBookingDateEnd() {
        return bookingDateEnd;
    }

    public void setBookingDateEnd(LocalDate bookingDateEnd) {
        this.bookingDateEnd = bookingDateEnd;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
