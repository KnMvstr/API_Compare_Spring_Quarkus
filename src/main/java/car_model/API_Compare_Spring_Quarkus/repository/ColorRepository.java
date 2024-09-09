package car_model.API_Compare_Spring_Quarkus.repository;

import car_model.API_Compare_Spring_Quarkus.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ColorRepository extends JpaRepository<Color, Long> {

}
