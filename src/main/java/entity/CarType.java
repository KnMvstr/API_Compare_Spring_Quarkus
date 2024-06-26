package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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
public class CarType extends PanacheEntityBase implements SluggerInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(JsonViews.CarType.class)
    private int id;

    @JsonView(JsonViews.CarTypeMin.class)
    private String name;

    @JsonIgnore
    private String slug;

    @ManyToMany(mappedBy = "carTypes")
    @Column(name = "carType")
    @JsonView(JsonViews.CarTypePlus.class)
    private List<Model> models = new ArrayList<>();

    @Override
    @JsonIgnore
    public String getField() {
        return name;
    }
}
