package car_model.API_Compare_Spring_Quarkus.service;

import car_model.API_Compare_Spring_Quarkus.dto.CarTypeDTO;
import car_model.API_Compare_Spring_Quarkus.entity.CarType;
import car_model.API_Compare_Spring_Quarkus.exception.NotFoundException;
import car_model.API_Compare_Spring_Quarkus.repository.CarTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarTypeService {

    private CarTypeRepository carTypeRepository;

    public List<CarType> getAllCarTypes() {
        return carTypeRepository.findAll();
    }

    public CarType getCarTypeById(Long id) {
        return carTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find car type with id: " + id));
    }

    public CarType persist(CarTypeDTO carTypeDTO, Long id) {
        CarType carType;
        if (id != null) {
            // Attempt to find the car type by ID
            carType = carTypeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Could not find car type with id: " + id));
            // Update the existing car type's fields
            carType.setName(carTypeDTO.getName());
            // Add any other fields from the DTO you wish to update
        } else {
            // If no ID is provided, create a new car type
            carType = new CarType();
            carType.setName(carTypeDTO.getName());
        }
        // Save the updated car type or the new car type
        return carTypeRepository.saveAndFlush(carType);
    }

    public void delete(Long id) {
        if (!carTypeRepository.existsById(id)) {
            throw new NotFoundException("Could not find car type with id: " + id);
        }
        carTypeRepository.deleteById(id);
    }
}