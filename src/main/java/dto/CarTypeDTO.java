package dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarTypeDTO {
    private Long id;
    @NotBlank(message = "Specify the car type")
    private String name;
    //List to stock only the model's name
    private List<String> modelNames;
}