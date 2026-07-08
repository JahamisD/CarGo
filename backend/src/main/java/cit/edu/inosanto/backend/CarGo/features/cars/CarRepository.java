package cit.edu.inosanto.backend.CarGo.features.cars;

import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Cars, Long> {

}