package car_model.API_Compare_Spring_Quarkus.repository;

import car_model.API_Compare_Spring_Quarkus.entity.Engine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EngineRepository extends JpaRepository<Engine, Long> {
}
