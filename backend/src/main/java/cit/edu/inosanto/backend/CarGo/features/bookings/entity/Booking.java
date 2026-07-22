package cit.edu.inosanto.backend.CarGo.features.bookings.entity;

import cit.edu.inosanto.backend.CarGo.features.users.entity.User;
import cit.edu.inosanto.backend.CarGo.features.cars.entity.Cars;
import jakarta.persistence.*;
import java.math.BigDecimal;


import java.time.LocalDate;



@Entity
@Table( name = "bookings")
public class Booking {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Cars getCar() {
        return car;
    }

    public void setCar(Cars car) {
        this.car = car;
    }

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Cars car;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private LocalDate bookingDateStart;
    private LocalDate bookingDateEnd;
    private String payment; // dropdown
    private String status; // pending, cancelled, confirmed
    private BigDecimal totalPrice;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

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
