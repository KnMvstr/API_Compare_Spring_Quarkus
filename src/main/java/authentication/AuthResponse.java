package authentication;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthResponse {
    public String jwttoken;
//    private String refreshToken;
}