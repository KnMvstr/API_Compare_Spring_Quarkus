package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Model extends PanacheEntityBase  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    @JsonIgnore
    private String slug;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToMany
    @JoinTable(
            name = "model_color",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    private List<Color> colors = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "engine_id", nullable = false)
    private Engine engine;

    @ManyToMany
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

    public enum Transmission {
        MANUAL, TORQUE, SEMI_AUTOMATIC, DUAL_CLUTCH, TRIPTONIC, CVT
    }
}