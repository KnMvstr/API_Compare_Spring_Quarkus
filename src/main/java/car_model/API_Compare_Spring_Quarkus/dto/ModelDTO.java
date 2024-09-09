package car_model.API_Compare_Spring_Quarkus.dto;

import car_model.API_Compare_Spring_Quarkus.entity.Brand;
import car_model.API_Compare_Spring_Quarkus.entity.CarType;
import car_model.API_Compare_Spring_Quarkus.entity.Color;
import car_model.API_Compare_Spring_Quarkus.entity.Engine;
import car_model.API_Compare_Spring_Quarkus.json_views.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelDTO {
    @NotBlank(message = "Specify the model name")
    private String name;

    private Brand brand;

    private List<Color> colors;

    @JsonView(JsonViews.EngineMin.class)
    private Engine engine;

    private List<CarType> carTypes;

    public enum Transmission {
        MANUAL, TORQUE, SEMI_AUTOMATIC, DUAL_CLUTCH, TRIPTONIC, CVT
    }
}