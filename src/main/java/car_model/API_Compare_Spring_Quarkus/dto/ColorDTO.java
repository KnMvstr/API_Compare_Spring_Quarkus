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
public class ColorDTO {
    @NotBlank(message = "Specify a name to the color")
    private String name;

    @NotBlank(message = "Specify the RVB equivalent")
    private String rvbRef;

    @NotBlank(message = "Specify the HEX equivalent")
    private String hexRef;
}