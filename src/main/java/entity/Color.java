package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
        property = "id"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Color extends PanacheEntityBase {
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