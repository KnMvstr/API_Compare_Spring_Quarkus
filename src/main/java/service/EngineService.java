package service;

import dto.EngineDTO;
import entity.Engine;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import repository.EngineRepository;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class EngineService {
    private EngineRepository engineRepository;

    public List<Engine> getAllEngines() {
        return engineRepository.listAll(Sort.by("name"));
    }

    public Engine getEngineById(Long id) {
        Engine engine = engineRepository.findById(id);
        if (engine == null) {
            throw new EntityNotFoundException("Could not find engine with id: " + id);
        }
        return engine;
    }

    public Engine persist(EngineDTO engineDTO, Long id) {
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
        return engine;
    }

    public void delete(Long id) {
        Engine engine = engineRepository.findById(id);
        if (engine == null) {
            throw new EntityNotFoundException("Could not find engine with id: " + id);
        }
        engineRepository.delete(engine);
    }
}