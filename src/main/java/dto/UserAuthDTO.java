package dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDTO {
    @NotBlank(message = "User must have a name ")
    private String username;

    @NotBlank(message = "User must have a email ")
    private String email;

    @NotBlank(message = "User must have a password")
    private String password;
}