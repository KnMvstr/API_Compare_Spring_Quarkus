package car_model.API_Compare_Spring_Quarkus.service;

import car_model.API_Compare_Spring_Quarkus.dto.BrandDTO;
import car_model.API_Compare_Spring_Quarkus.entity.Brand;
import car_model.API_Compare_Spring_Quarkus.exception.NotFoundException;
import car_model.API_Compare_Spring_Quarkus.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find brand with id: " + id));
    }

    public Brand persist(BrandDTO brandDTO, Long id) {
        Brand brand;
        if (id != null) {
            // Attempt to find the brand by ID
            brand = brandRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Could not find brand with id: " + id));
            // Update the existing brand's fields
            brand.setName(brandDTO.getName());
            // Add any other fields from the DTO you wish to update
        } else {
            // If no ID is provided, create a new brand
            brand = new Brand();
            brand.setName(brandDTO.getName());
        }
        // Save the updated brand or the new brand
        return brandRepository.saveAndFlush(brand);
    }

    public void delete(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new NotFoundException("Could not find brand with id: " + id);
        }
        brandRepository.deleteById(id);
    }
}