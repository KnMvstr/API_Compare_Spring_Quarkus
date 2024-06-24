package car_model.API_Compare_Spring_Quarkus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLoginDTO {
    private String username;
    private String password;
}