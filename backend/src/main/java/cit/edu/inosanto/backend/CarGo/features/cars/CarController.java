package cit.edu.inosanto.backend.CarGo.features.cars;

import cit.edu.inosanto.backend.CarGo.features.cars.Cars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cit.edu.inosanto.backend.CarGo.features.cars.CarRepository;
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
}