package dto;

import entity.Brand;
import entity.CarType;
import entity.Color;
import entity.Engine;
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

    private Engine engine;

    private List<CarType> carTypes;

    public enum Transmission {
        MANUAL, TORQUE, SEMI_AUTOMATIC, DUAL_CLUTCH, TRIPTONIC, CVT
    }
}
