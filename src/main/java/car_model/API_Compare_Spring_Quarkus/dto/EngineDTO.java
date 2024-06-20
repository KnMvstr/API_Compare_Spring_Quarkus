package car_model.API_Compare_Spring_Quarkus.dto;

import car_model.API_Compare_Spring_Quarkus.entity.Model;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngineDTO {
    @NotBlank(message = "Specify the engine reference")
    private String name;

    private String power;

    private FuelType fuelType;

    private List<Model> models;

    public enum FuelType {
        HYDROGENE, GASOLINE, ELECTRIC, ETHANOL, HOLY_WATER, DIESEL, HYBRID;
    }
}