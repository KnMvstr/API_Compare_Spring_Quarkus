package car_model.API_Compare_Spring_Quarkus.repository;

import car_model.API_Compare_Spring_Quarkus.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
}
