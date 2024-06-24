package car_model.API_Compare_Spring_Quarkus.entity;

import car_model.API_Compare_Spring_Quarkus.json_views.JsonViews;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(JsonViews.Color.class)
    private int id;

    @JsonView(JsonViews.ColorMin.class)
    private String name;

    @JsonView(JsonViews.ColorAdd.class)
    private String rvbRef;

    @JsonView(JsonViews.ColorAdd.class)
    private String hexRef;

    @ManyToMany(mappedBy = "colors")
    @JsonView(JsonViews.ColorPlus.class)
    private List<Model> models = new ArrayList<>();
}