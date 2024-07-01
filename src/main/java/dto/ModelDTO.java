package dto;

import entity.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelDTO {
    private Long id;
    @NotBlank(message = "Specify the model name")
    private String name;

    private String brandName;

    private String engineName;

    private List<String> colorNames;

    private List<String> carTypeNames;

    private Set<Transmission> transmissions;
}