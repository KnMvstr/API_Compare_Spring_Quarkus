package car_model.API_Compare_Spring_Quarkus.service;

import car_model.API_Compare_Spring_Quarkus.dto.ModelDTO;
import car_model.API_Compare_Spring_Quarkus.entity.Model;
import car_model.API_Compare_Spring_Quarkus.exception.NotFoundException;
import car_model.API_Compare_Spring_Quarkus.repository.ModelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ModelService {

    ModelRepository modelRepository;

    public List<Model> getAllModels() {
        return modelRepository.findAll();
    }

    public Model getModelById(Long id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find model with id: " + id));
    }

    public Model persist(ModelDTO modelDTO, Long id) {
        Model model;
        if (id != null) {
            // Attempt to find the model by ID
            model = modelRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Could not find model with id: " + id));
            // Update the existing model's fields
            model.setName(modelDTO.getName());
            // Add any other fields from the DTO you wish to update
        } else {
            // If no ID is provided, create a new model
            model = new Model();
            model.setName(modelDTO.getName());
        }
        // Save the updated model or the new model
        return modelRepository.saveAndFlush(model);
    }

    public void delete(Long id) {
        if (!modelRepository.existsById(id)) {
            throw new NotFoundException("Could not find model with id: " + id);
        }
        modelRepository.deleteById(id);
    }
}