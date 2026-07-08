package cit.edu.inosanto.backend.CarGo.features.bookings;

import java.time.LocalDate;

public class BookingRequest {
    private Long bookId;
    private LocalDate bookingDateStart;
    private LocalDate bookingDateEnd;
    private String payment;
    private String status;

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
