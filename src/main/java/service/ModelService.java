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
import repository.ModelRepository;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class ModelService {
    ModelRepository modelRepository;

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
        Model model;
        if (id != null) {
            model = modelRepository.findById(id);
            if (model == null) {
                throw new EntityNotFoundException("Could not find model with id: " + id);
            }
        } else {
            model = new Model();
        }
        model.setName(modelDTO.getName());
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
        dto.setBrandName(model.getBrand().getName());
        dto.setEngineName(model.getEngine().getName());
        dto.setTransmissions(new HashSet<>(model.getTransmissions()));

        // The model must return the list of color linked
        List<String> colorNames = model.getColors().stream()
                .map(Color::getName)
                .collect(Collectors.toList());
        dto.setColorNames(colorNames);

        // The model must return the list of cartype linked
        List<String> carTypeNames = model.getCarTypes().stream()
                .map(CarType::getName)
                .toList();
        dto.setCarTypeNames(carTypeNames);
        return dto;
    }
}