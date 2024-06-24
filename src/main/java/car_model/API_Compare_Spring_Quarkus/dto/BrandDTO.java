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
public class BrandDTO {
    @NotBlank(message = "Specify the name of the brand")
    private String name;
}