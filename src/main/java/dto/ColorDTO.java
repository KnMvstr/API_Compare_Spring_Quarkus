package dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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