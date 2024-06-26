package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
        property = "id"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Brand implements SluggerInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(JsonViews.Brand.class)
    private Long id;

    @JsonView(JsonViews.BrandMin.class)
    private String name;

    @JsonIgnore
    private String slug;

    @OneToMany(mappedBy = "brand")
    @JsonView(JsonViews.BrandPlus.class)
    private List<Model> models = new ArrayList<>();

    @Override
    @JsonIgnore
    public String getField() {
        return name;
    }
}
