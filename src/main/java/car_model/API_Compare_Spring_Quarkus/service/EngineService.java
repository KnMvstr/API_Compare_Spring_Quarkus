package car_model.API_Compare_Spring_Quarkus.service;

import car_model.API_Compare_Spring_Quarkus.dto.EngineDTO;
import car_model.API_Compare_Spring_Quarkus.entity.Engine;
import car_model.API_Compare_Spring_Quarkus.exception.NotFoundException;
import car_model.API_Compare_Spring_Quarkus.repository.EngineRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EngineService {

    private EngineRepository engineRepository;

    public List<Engine> getAllEngines() {
        return engineRepository.findAll();
    }

    public Engine getEngineById(Long id) {
        return engineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find engine with id: " + id));
    }

    public Engine persist(EngineDTO engineDTO, Long id) {
        Engine engine;
        if (id != null) {
            // Attempt to find the engine by ID
            engine = engineRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Could not find engine with id: " + id));
            // Update the existing engine's fields
            engine.setName(engineDTO.getName());
            // Add any other fields from the DTO you wish to update
        } else {
            // If no ID is provided, create a new engine
            engine = new Engine();
            engine.setName(engineDTO.getName());
        }
        // Save the updated engine or the new engine
        return engineRepository.saveAndFlush(engine);
    }

    public void delete(Long id) {
        if (!engineRepository.existsById(id)) {
            throw new NotFoundException("Could not find engine with id: " + id);
        }
        engineRepository.deleteById(id);
    }
}