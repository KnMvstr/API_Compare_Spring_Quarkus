package car_model.API_Compare_Spring_Quarkus.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {
    @NotBlank(message = "Specify the name of the brand")
    private String name;
}