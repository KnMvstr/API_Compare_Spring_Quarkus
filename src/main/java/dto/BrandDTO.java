package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {
    private Long id;
    @NotBlank(message = "Specify the name of the brand")
    private String name;

    //List to stock only the model's name
    private List<String> modelNames;
}
