package car_model.API_Compare_Spring_Quarkus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import car_model.API_Compare_Spring_Quarkus.entity.Color;


public interface ColorRepository extends JpaRepository<Color, Long> {

}
