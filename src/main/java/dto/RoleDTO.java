package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    @Schema(description = "The name of the role", example = "ADMIN / USER", required = true)
    private String name;
}
