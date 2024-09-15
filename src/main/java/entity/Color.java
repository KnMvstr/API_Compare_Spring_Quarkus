package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dto.ColorDTO;
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
public class Color extends PanacheEntity {
    private String name;

    private String rvbRef;

    private String hexRef;

    @ManyToMany(mappedBy = "colors", fetch = FetchType.EAGER)
    private List<Model> models = new ArrayList<>();

    public static List<ColorDTO> getAllColors() {
        List<Color> colors = Color.listAll(Sort.by("name"));
        return colors.stream().map(Color::toDTO).collect(Collectors.toList());
    }

    public static ColorDTO getColorById(Long id) {
        Color color = Color.findById(id);
        if (color == null) {
            throw new EntityNotFoundException("Could not find color with id: " + id);
        }
        return toDTO(color);
    }

    public static ColorDTO persist(ColorDTO colorDTO, Long id) {
        Color color;
        if (id != null) {
            color = Color.findById(id);
            if (color == null) {
                throw new EntityNotFoundException("Could not find color with id: " + id);
            }
        } else {
            color = new Color();
        }
        color.setName(colorDTO.getName());
        color.setRvbRef(colorDTO.getRvbRef());
        color.setHexRef(colorDTO.getHexRef());
        Brand.persist(color);
        return toDTO(color);
    }

    public static void delete(Long id) {
        Color color = Color.findById(id);
        if (color == null) {
            throw new EntityNotFoundException("Could not find color with id: " + id);
        }
        Color.deleteById(color);
    }

    public static Color fromDTO(ColorDTO dto) {
        Color color = new Color();
        color.name = dto.getName();
        color.rvbRef = dto.getRvbRef();
        color.hexRef = dto.getHexRef();
        return color;
    }

    public void updateFromDTO(ColorDTO dto) {
        this.name = dto.getName();
        this.rvbRef = dto.getRvbRef();
        this.hexRef = dto.getHexRef();
    }

    public static ColorDTO toDTO(Color color) {
        ColorDTO dto = new ColorDTO();
        //dto.setId(color.id); //Live Coding Scenario 1
        dto.setName(color.name);
        dto.setRvbRef(color.rvbRef);
        dto.setHexRef(color.hexRef);
        dto.setModelNames(color.getModels() != null
                ? color.getModels().stream().map(Model::getName).collect(Collectors.toList())
                : null);
        return dto;
    }
}