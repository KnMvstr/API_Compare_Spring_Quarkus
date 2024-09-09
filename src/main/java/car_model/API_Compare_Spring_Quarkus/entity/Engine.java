package car_model.API_Compare_Spring_Quarkus.entity;

import car_model.API_Compare_Spring_Quarkus.json_views.JsonViews;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Entity
public class Engine  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(JsonViews.Engine.class)
    private int id;

    @JsonView(JsonViews.EngineMin.class)
    private String name;

    @JsonView(JsonViews.Engine.class)
    private String power;

    @JsonView(JsonViews.EnginePlus.class)
    private FuelType fuelType;


    @OneToMany(mappedBy = "engine")
    @JsonView(JsonViews.EnginePlus.class)
    private List<Model> models = new ArrayList<>();

    public enum FuelType {
        HYDROGENE, GASOLINE, ELECTRIC, ETHANOL, HOLY_WATER, DIESEL, HYBRID;
    }
}