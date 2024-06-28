package dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorDTO {
    private Long id;

    @NotBlank(message = "Specify a name to the color")
    private String name;

    @NotBlank(message = "Specify the RVB equivalent")
    private String rvbRef;

    @NotBlank(message = "Specify the HEX equivalent")
    private String hexRef;

    //List to stock only the model's name
    private List<String> modelNames;
}