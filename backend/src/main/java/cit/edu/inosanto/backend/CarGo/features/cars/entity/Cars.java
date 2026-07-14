package cit.edu.inosanto.backend.CarGo.features.cars.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table( name = "cars")
public class Cars {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    private String carName;
    private String carDetails;
    private String carBrand;
    private String carOwner;
    private BigDecimal carPrice;
    private String plateNumber;

    public BigDecimal getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(BigDecimal carPrice) {
        this.carPrice = carPrice;
    }


    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) { this.carId = carId;
    }

    public String getCarName() { return carName;
    }


    public String getPlateNumber() { return plateNumber;
    }

    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(String carDetails) {
        this.carDetails = carDetails;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }
}
