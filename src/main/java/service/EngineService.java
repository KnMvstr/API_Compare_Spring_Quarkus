package service;

import dto.BrandDTO;
import dto.EngineDTO;
import entity.Brand;
import entity.Engine;
import entity.Model;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import repository.EngineRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class EngineService {
    private EngineRepository engineRepository;

    public List<EngineDTO> getAllEngines() {
        List<Engine> engines = engineRepository.listAll(Sort.by("name"));
        return engines.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public EngineDTO getEngineById(Long id) {
        Engine engine = engineRepository.findById(id);
        if (engine == null) {
            throw new EntityNotFoundException("Could not find engine with id: " + id);
        }
        return toDTO(engine);
    }

    public EngineDTO persist(EngineDTO engineDTO, Long id) {
        Engine engine;
        if (id != null) {
            engine = engineRepository.findById(id);
            if (engine == null) {
                throw new EntityNotFoundException("Could not find engine with id: " + id);
            }
        } else {
            engine = new Engine();
        }
        engine.setName(engineDTO.getName());
        engineRepository.persist(engine);
        return toDTO(engine);
    }

    public void delete(Long id) {
        Engine engine = engineRepository.findById(id);
        if (engine == null) {
            throw new EntityNotFoundException("Could not find engine with id: " + id);
        }
        engineRepository.delete(engine);
    }

    // In our configuration it is mandatory to convert our plain object into a DTO.
    // We avoid the LazyLoading exception throw due to our relations between entities.
    private EngineDTO toDTO(Engine engine) {
        EngineDTO dto = new EngineDTO();
        dto.setId(engine);
        dto.setName(engine.getName());
        dto.setPower(engine.getPower());
        dto.setFuelType(engine.getFuelType());

        // The engine must return the list of model linked
        List<String> modelNames = engine.getModels().stream()
                .map(Model::getName)
                .collect(Collectors.toList());
        dto.setModelNames(modelNames);
        return dto;
    }
}
