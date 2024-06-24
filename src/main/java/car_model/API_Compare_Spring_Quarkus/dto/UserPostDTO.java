package car_model.API_Compare_Spring_Quarkus.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPostDTO {

    @NotBlank(message = "Please, give an username")
    @Column(unique= true, nullable = false, length = 5)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(message = "The password must have at least 5 characters", min = 5)
    private String password;

    private String role;

    public boolean isAdmin() {
        return "ROLE_ADMIN".equals(role);  // Checks if the role is ADMIN
    }

}