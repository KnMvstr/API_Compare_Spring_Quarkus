package service;

import dto.ModelDTO;
import entity.Model;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import repository.ModelRepository;
import java.util.List;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class ModelService {
    ModelRepository modelRepository;

    public List<Model> getAllModels() {
        return modelRepository.listAll(Sort.by("name"));
    }

    public Model getModelById(Long id) {
        Model model = modelRepository.findById(id);
        if (model == null) {
            throw new EntityNotFoundException("Could not find model with id: " + id);
        }
        return model;
    }

    public Model persist(ModelDTO modelDTO, Long id) {
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
        return model;
    }

    public void delete(Long id) {
        Model model = modelRepository.findById(id);
        if (model == null) {
            throw new EntityNotFoundException("Could not find model with id: " + id);
        }
        modelRepository.delete(model);
    }
}