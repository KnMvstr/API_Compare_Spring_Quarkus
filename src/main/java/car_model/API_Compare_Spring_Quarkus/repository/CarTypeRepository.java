package car_model.API_Compare_Spring_Quarkus.repository;

import car_model.API_Compare_Spring_Quarkus.entity.CarType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarTypeRepository extends JpaRepository<CarType, Long> {
}
