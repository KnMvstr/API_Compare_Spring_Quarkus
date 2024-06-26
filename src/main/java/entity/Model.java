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
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Model implements SluggerInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(JsonViews.Model.class)
    private Long id;

    @JsonView(JsonViews.ModelMin.class)
    private String name;

    @JsonIgnore
    private String slug;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    @JsonView(JsonViews.ModelPlus.class)
    private Brand brand;

    @ManyToMany
    @JoinTable(
            name = "model_color",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    @JsonView(JsonViews.ModelPlus.class)
    private List<Color> colors = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "engine_id", nullable = false)
    @JsonView(JsonViews.ModelPlus.class)
    private Engine engine;

    @ManyToMany
    @JoinTable(
            name = "model_carType",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "carType_id")
    )
    @JsonView(JsonViews.ModelPlus.class)
    private List<CarType> carTypes = new ArrayList<>();

    @ElementCollection(targetClass = Transmission.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "car_transmissions", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "transmission")
    @JsonView(JsonViews.ModelPlus.class)
    private Set<Transmission> transmissions;

    @Override
    @JsonIgnore
    public String getField() {
        return name;
    }

    public enum Transmission {
        MANUAL, TORQUE, SEMI_AUTOMATIC, DUAL_CLUTCH, TRIPTONIC, CVT
    }
}