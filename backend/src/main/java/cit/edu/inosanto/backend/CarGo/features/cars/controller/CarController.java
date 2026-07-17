package cit.edu.inosanto.backend.CarGo.features.cars.controller;

import cit.edu.inosanto.backend.CarGo.features.cars.entity.Cars;
import cit.edu.inosanto.backend.CarGo.features.cars.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private CarRepository carRepository;


    @PostMapping("/cars")
    public Cars createCar(@RequestBody Cars newCar) {
        return carRepository.save(newCar);
    }

    @GetMapping("/cars")
    public List<Cars> getCars() {
        return carRepository.findAll();
    }

    @GetMapping("/cars/{id}")
    public Cars getCar(@PathVariable Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @PutMapping("/cars/{id}")
    public Cars updateCar(
            @PathVariable Long id,
            @RequestBody Cars updatedCar
    ) {

        Cars existingCar = carRepository.findById(id)
                .orElse(null);

        if (existingCar == null) {
            return null;
        }


        existingCar.setBrand(updatedCar.getBrand());
        existingCar.setModel(updatedCar.getModel());
        existingCar.setYear(updatedCar.getYear());
        existingCar.setDetails(updatedCar.getDetails());
        existingCar.setPricePerDay(updatedCar.getPricePerDay());
        existingCar.setImageUrl(updatedCar.getImageUrl());
        existingCar.setPlateNumber(updatedCar.getPlateNumber());


        return carRepository.save(existingCar);
    }

    @DeleteMapping("/cars/{id}")
    public void deleteCar(@PathVariable Long id) {

        carRepository.deleteById(id);

    }

}