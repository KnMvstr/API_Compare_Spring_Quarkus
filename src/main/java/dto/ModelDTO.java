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

    private Long brandId;

    private Long engineId;

    private List<Long> colorId;

    private List<Long> carTypeId;

    private Set<Transmission> transmissions;
}