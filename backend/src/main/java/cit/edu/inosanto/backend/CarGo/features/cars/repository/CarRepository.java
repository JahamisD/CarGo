package cit.edu.inosanto.backend.CarGo.features.cars.repository;

import cit.edu.inosanto.backend.CarGo.features.cars.entity.Cars;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Cars, Long> {

}