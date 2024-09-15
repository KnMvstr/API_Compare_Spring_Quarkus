package dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "User must have a name ")
    private String username;

    @NotBlank(message = "User must have a email ")
    private String email;

    @NotBlank(message = "User must have a password")
    private String password;

    @Schema(description = "The role of the user")
    private RoleDTO role;
}