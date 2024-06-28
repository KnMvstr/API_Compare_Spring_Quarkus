package dto;

import entity.FuelType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngineDTO {
    private Long id;
    @NotBlank(message = "Specify the engine reference")
    private String name;

    @NotBlank(message = "Specify the power")
    private String power;

    private FuelType fuelType;

    //List to stock only the model's name
    private List<String> modelNames;
}