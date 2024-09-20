package car_model.API_Compare_Spring_Quarkus.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @JsonIgnore
    private boolean admin; // This field represents whether the user is an admin act like a toggle

    @JsonIgnore //Must be hidden in the swagger
    public String getRoles() {
        return isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
    }
}