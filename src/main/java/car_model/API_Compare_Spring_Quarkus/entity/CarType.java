package car_model.API_Compare_Spring_Quarkus.entity;

import car_model.API_Compare_Spring_Quarkus.json_views.JsonViews;
import car_model.API_Compare_Spring_Quarkus.utils.SluggerInterface;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name",
        scope= JsonViews.CarType.class
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CarType implements SluggerInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(JsonViews.CarType.class)
    private int id;

    @JsonView(JsonViews.CarType.class)
    private String name;

    private String slug;

    @ManyToMany(mappedBy = "carTypes")
    @Column(name = "carType")
    @JsonView(JsonViews.CarTypePlus.class)
    private List<Model> models = new ArrayList<>();

    @Override
    public String getField() {
        return name;
    }
}