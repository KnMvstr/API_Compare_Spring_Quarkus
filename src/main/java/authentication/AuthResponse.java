package authentication;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class AuthResponse {
    private String jwttoken;
}