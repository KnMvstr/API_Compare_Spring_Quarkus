package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.slugify.Slugify;
import dto.BrandDTO;
import dto.CarTypeDTO;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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
        property = "name"
)
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CarType extends PanacheEntity {

    private String name;

    @JsonIgnore
    private String slug;

    @ManyToMany(mappedBy = "carTypes", fetch = FetchType.EAGER)
    @Column(name = "carType")
    private List<Model> models = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void generateSlug() {
        Slugify slugify = Slugify.builder().build();
        this.slug = slugify.slugify(this.name);
    }

    public static List<CarTypeDTO> getAllCarTypes() {
        List<CarType> carTypes = CarType.listAll(Sort.by("name"));
        return carTypes.stream().map(CarType::toDTO).collect(Collectors.toList());
    }

    public static CarTypeDTO getCarTypeById(Long id) {
        CarType carType = CarType.findById(id);
        if (carType == null) {
            throw new EntityNotFoundException("Could not find carType with id: " + id);
        }
        return toDTO(carType);
    }

    public CarTypeDTO persist(CarTypeDTO carTypeDTO, Long id) {
        CarType carType;
        if (id != null) {
            carType = CarType.findById(id);
            if (carType == null) {
                throw new EntityNotFoundException("Could not find carType with id: " + id);
            }
        } else {
            carType = new CarType();
        }
        carType.setName(carTypeDTO.getName());
        CarType.persist(carType);
        return toDTO(carType);
    }

    public void delete(Long id) {
        CarType carType = CarType.findById(id);
        if (carType == null) {
            throw new EntityNotFoundException("Could not find carType with id: " + id);
        }
        CarType.deleteById(carType);
    }

    public static CarType fromDTO(CarTypeDTO dto) {
        CarType carType = new CarType();
        carType.name = dto.getName();
        return carType;
    }

    public void updateFromDTO(CarTypeDTO dto) {
        this.name = dto.getName();
    }

    public static CarTypeDTO toDTO(CarType carType) {
        CarTypeDTO dto = new CarTypeDTO();
        dto.setId(carType.id);
        dto.setName(carType.name);
        return dto;
    }

}