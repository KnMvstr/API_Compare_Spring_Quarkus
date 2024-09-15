package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.slugify.Slugify;
import dto.ModelDTO;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Model extends PanacheEntity {

    private String name;

    @JsonIgnore
    private String slug;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", nullable = false)
    @JsonBackReference // To prevent recursive loop
    private Brand brand;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "model_color",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    private List<Color> colors = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "engine_id", nullable = false)
    @JsonBackReference // To prevent recursive loop
    private Engine engine;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "model_carType",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "carType_id")
    )
    private List<CarType> carTypes = new ArrayList<>();

    @ElementCollection(targetClass = Transmission.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "car_transmissions", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "transmission")
    private Set<Transmission> transmissions;

    // Automatically generate the slug before persisting or updating
    @PrePersist
    @PreUpdate
    public void generateSlug() {
        Slugify slugify = Slugify.builder().build();
        this.slug = slugify.slugify(this.name);
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                '}';
    }

    public List<ModelDTO> getAllModels() {
        List<Model> models = Model.listAll(Sort.by("name"));
        return models.stream().map(Model::toDTO).collect(Collectors.toList());
    }

    public ModelDTO getModelById(Long id) {
        Model model = Model.findById(id);
        if (model == null) {
            throw new EntityNotFoundException("Could not find model with id: " + id);
        }
        return toDTO(model);
    }

    public static ModelDTO persist(ModelDTO modelDTO, Long id) {
        Model model;

        // Vérification si un modèle existe déjà avec cet ID
        if (id != null) {
            model = Model.findById(id);
            if (model == null) {
                throw new EntityNotFoundException("Could not find model with id: " + id);
            }
        } else {
            model = new Model();
        }
        model.setName(modelDTO.getName());
        // Update the model's attributes from the DTO
        Brand brand = Brand.find("name", modelDTO.getBrandName()).firstResult();
        if (brand == null) {
            throw new EntityNotFoundException("Brand not found with name: " + modelDTO.getBrandName());
        }
        model.setBrand(brand);

        // Retrieve the engine by name from the DTO and associate it with the model
        Engine engine = Engine.find("name", modelDTO.getEngineName()).firstResult();
        if (engine == null) {
            throw new EntityNotFoundException("Engine not found with name: " + modelDTO.getEngineName());
        }
        model.setEngine(engine);

        // Retrieve and associate colors with the model based on the color names in the DTO
        List<Color> colors = Color.find("name in ?1", modelDTO.getColorNames()).list();
        if (colors.isEmpty()) {
            throw new EntityNotFoundException("No colors found for names: " + modelDTO.getColorNames());
        }
        model.setColors(colors);

        // Retrieve and associate car types based on the car type names in the DTO
        List<CarType> carTypes = CarType.find("name in ?1", modelDTO.getCarTypeNames()).list();
        if (carTypes.isEmpty()) {
            throw new EntityNotFoundException("No car types found for names: " + modelDTO.getCarTypeNames());
        }
        model.setCarTypes(carTypes);
        // Set transmissions from the DTO
        model.setTransmissions(modelDTO.getTransmissions());
        Model.persist(model);

        // Return the updated DTO
        return toDTO(model);
    }

    public static void delete(Long id) {
        Model model = Model.findById(id);
        if (model == null) {
            throw new EntityNotFoundException("Could not find model with id: " + id);
        }
        Model.deleteById(model);
    }

    public static Model fromDTO(ModelDTO modelDTO) {
        Model model = new Model();
        // Set the basic fields like name
        model.setName(modelDTO.getName());

        // Retrieve and set the Brand using the brand name from the DTO
        Brand brand = Brand.find("name", modelDTO.getBrandName()).firstResult();
        if (brand == null) {
            throw new EntityNotFoundException("Brand not found with name: " + modelDTO.getBrandName());
        }
        model.setBrand(brand);

        // Retrieve and set the Engine using the engine name from the DTO
        Engine engine = Engine.find("name", modelDTO.getEngineName()).firstResult();
        if (engine == null) {
            throw new EntityNotFoundException("Engine not found with name: " + modelDTO.getEngineName());
        }
        model.setEngine(engine);

        // Retrieve and associate Colors based on the color names in the DTO
        List<Color> colors = Color.find("name in ?1", modelDTO.getColorNames()).list();
        if (colors.isEmpty()) {
            throw new EntityNotFoundException("No colors found for names: " + modelDTO.getColorNames());
        }
        model.setColors(colors);

        // Retrieve and associate CarTypes based on the car type names in the DTO
        List<CarType> carTypes = CarType.find("name in ?1", modelDTO.getCarTypeNames()).list();
        if (carTypes.isEmpty()) {
            throw new EntityNotFoundException("No car types found for names: " + modelDTO.getCarTypeNames());
        }
        model.setCarTypes(carTypes);

        // Set the transmissions directly from the DTO
        model.setTransmissions(modelDTO.getTransmissions());

        return model;
    }

    public void updateFromDTO(ModelDTO modelDTO) {
        this.name = modelDTO.getName();
        // Update the brand associated with the model
        Brand brand = Brand.find("name", modelDTO.getBrandName()).firstResult();
        if (brand == null) {
            throw new EntityNotFoundException("Brand not found with name: " + modelDTO.getBrandName());
        }
        this.setBrand(brand);

        // Update the engine associated with the model
        Engine engine = Engine.find("name", modelDTO.getEngineName()).firstResult();
        if (engine == null) {
            throw new EntityNotFoundException("Engine not found with name: " + modelDTO.getEngineName());
        }
        this.setEngine(engine);

        // Update the list of colors associated with the model
        List<Color> colors = Color.find("name in ?1", modelDTO.getColorNames()).list();
        if (colors.isEmpty()) {
            throw new EntityNotFoundException("No colors found for names: " + modelDTO.getColorNames());
        }
        this.setColors(colors);

        // Update the list of car types associated with the model
        List<CarType> carTypes = CarType.find("name in ?1", modelDTO.getCarTypeNames()).list();
        if (carTypes.isEmpty()) {
            throw new EntityNotFoundException("No car types found for names: " + modelDTO.getCarTypeNames());
        }
        this.setCarTypes(carTypes);

        // Update the transmissions from the DTO
        this.setTransmissions(modelDTO.getTransmissions());
    }

    public static ModelDTO toDTO(Model model) {
        ModelDTO dto = new ModelDTO();
        dto.setId(model.id);
        dto.setName(model.name);

        // Retrieve only the brand name
        dto.setBrandName(model.getBrand() != null ? model.getBrand().getName() : null);
        // Retrieve only the engine name
        dto.setEngineName(model.getEngine() != null ? model.getEngine().getName() : null);
        // Retrieve only the color names
        dto.setColorNames(model.getColors() != null
                ? model.getColors().stream().map(Color::getName).collect(Collectors.toList())
                : null);
        // Retrieve only the car type names
        dto.setCarTypeNames(model.getCarTypes() != null
                ? model.getCarTypes().stream().map(CarType::getName).collect(Collectors.toList())
                : null);
        // Transmissions are already strings
        dto.setTransmissions(model.getTransmissions());

        return dto;
    }
}