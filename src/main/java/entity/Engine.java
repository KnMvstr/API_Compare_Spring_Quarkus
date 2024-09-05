package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dto.EngineDTO;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Engine extends PanacheEntity {
    private String name;

    private String power;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @OneToMany(mappedBy = "engine", fetch = FetchType.EAGER)
    private List<Model> models = new ArrayList<>();

    public static List<EngineDTO> getAllEngines() {
        List<Engine> engines = Engine.listAll(Sort.by("name"));
        return engines.stream().map(Engine::toDTO).collect(Collectors.toList());
    }

    public static EngineDTO getEngineById(Long id) {
        Engine engine = Engine.findById(id);
        if (engine == null) {
            throw new EntityNotFoundException("Could not find engine with id: " + id);
        }
        return toDTO(engine);
    }

    public EngineDTO persist(EngineDTO engineDTO, Long id) {
        Engine engine;
        if (id != null) {
            engine = Engine.findById(id);
            if (engine == null) {
                throw new EntityNotFoundException("Could not find engine with id: " + id);
            }
        } else {
            engine = new Engine();
        }
        engine.setName(engineDTO.getName());
        engine.setPower(engineDTO.getPower());
        engine.setFuelType(engineDTO.getFuelType());
        engine.setModels(models);
        Engine.persist(engine);
        return toDTO(engine);
    }

    public void delete(Long id) {
        Engine engine = Engine.findById(id);
        if (engine == null) {
            throw new EntityNotFoundException("Could not find engine with id: " + id);
        }
        Engine.deleteById(engine);
    }

    public static Engine fromDTO(EngineDTO dto) {
        Engine engine = new Engine();
        engine.name = dto.getName();
        engine.power = dto.getPower();
        engine.fuelType = dto.getFuelType();
        //engine.models = dto.getModelNames();
        return engine;
    }

    public void updateFromDTO(EngineDTO dto) {
        this.name = dto.getName();
        this.power = dto.getPower();
        this.fuelType = dto.getFuelType();
    }

    public static EngineDTO toDTO(Engine engine) {
        EngineDTO dto = new EngineDTO();
        dto.setId(engine.id);
        dto.setName(engine.name);
        dto.setFuelType(engine.fuelType);
        dto.setPower(engine.power);
        dto.setModelNames(engine.getModels() != null
                ? engine.getModels().stream().map(Model::getName).collect(Collectors.toList())
                : null);
        return dto;
    }
}