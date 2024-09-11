package service;

import dto.ModelDTO;
import entity.CarType;
import entity.Model;
import entity.Color;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import repository.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class ModelService {
    BrandRepository brandRepository;
    ModelRepository modelRepository;
    EngineRepository engineRepository;
    ColorRepository colorRepository;
    CarTypeRepository carTypeRepository;

    public List<ModelDTO> getAllModels() {
        List<Model> models = modelRepository.listAll(Sort.by("name"));
        return models.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ModelDTO getModelById(Long id) {
        Model model = modelRepository.findById(id);
        if (model == null) {
            throw new EntityNotFoundException("Could not find model with id: " + id);
        }
        return toDTO(model);
    }

    public ModelDTO persist(ModelDTO modelDTO, Long id) {
        Model model = (id != null) ? modelRepository.findById(id) : new Model();
        if (model == null) {
            throw new EntityNotFoundException("Could not find model with id: " + id);
        }

        model.setName(modelDTO.getName());

        if (modelDTO.getBrandId() != null) {
            model.setBrand(brandRepository.findById(modelDTO.getBrandId()));
        } else {
            throw new IllegalArgumentException("Brand ID must not be null");
        }

        if (modelDTO.getEngineId() != null) {
            model.setEngine(engineRepository.findById(modelDTO.getEngineId()));
        } else {
            throw new IllegalArgumentException("Engine ID must not be null");
        }

        // Handle Color IDs
        if (modelDTO.getColorId() != null && !modelDTO.getColorId().isEmpty()) {
            List<Color> colors = colorRepository.findByIds(modelDTO.getColorId());
            model.setColors(colors);
        }

        // Handle Car Type IDs
        if (modelDTO.getCarTypeId() != null && !modelDTO.getCarTypeId().isEmpty()) {
            List<CarType> carTypes = carTypeRepository.findByIds(modelDTO.getCarTypeId());
            model.setCarTypes(carTypes);
        }

        modelRepository.persist(model);
        return toDTO(model);
    }

    public void delete(Long id) {
        Model model = modelRepository.findById(id);
        if (model == null) {
            throw new EntityNotFoundException("Could not find model with id: " + id);
        }
        modelRepository.delete(model);
    }

    // In our configuration it is mandatory to convert our plain object into a DTO.
    // We avoid the LazyLoading exception throw due to our relations between entities.
    private ModelDTO toDTO(Model model) {
        ModelDTO dto = new ModelDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setBrandId(model.getBrand() != null ? model.getBrand().getId() : null);
        dto.setEngineId(model.getEngine() != null ? model.getEngine().getId() : null);
        dto.setColorId(model.getColors().stream().map(Color::getId).collect(Collectors.toList()));
        dto.setCarTypeId(model.getCarTypes().stream().map(CarType::getId).collect(Collectors.toList()));

        // Check if transmissions is null before creating a HashSet
        if (model.getTransmissions() != null) {
            dto.setTransmissions(new HashSet<>(model.getTransmissions()));
        } else {
            dto.setTransmissions(new HashSet<>());  // Initialize with an empty set if null
        }

        return dto;
    }
}