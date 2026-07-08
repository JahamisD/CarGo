package cit.edu.inosanto.backend.CarGo.features.cars;

import cit.edu.inosanto.backend.CarGo.features.cars.CarRepository;

public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository){
        this.carRepository = carRepository;
    }


}
