package car_model.API_Compare_Spring_Quarkus.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarTypeDTO {
    @NotBlank(message = "Specify the car type")
    private String name;
}