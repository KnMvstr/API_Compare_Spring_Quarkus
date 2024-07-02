package dto;

import entity.Role;
import io.quarkus.security.jpa.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "User must have a name ")
    @NotNull
    private String username;

    @NotBlank(message = "User must have a password")
    private String password;

    @NotBlank(message = "User must have a role")
    private Role role;
}