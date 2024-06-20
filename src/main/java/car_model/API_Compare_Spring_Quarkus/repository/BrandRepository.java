package car_model.API_Compare_Spring_Quarkus.repository;

import car_model.API_Compare_Spring_Quarkus.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
