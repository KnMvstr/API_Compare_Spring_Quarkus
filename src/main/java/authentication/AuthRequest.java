package authentication;

import jakarta.ws.rs.FormParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @FormParam("username")
    private String username;

    @FormParam("password")
    private String password;

//    @FormParam("refresh_token")
//    @Schema(name = "refresh_token")
//    private String refreshToken;
}