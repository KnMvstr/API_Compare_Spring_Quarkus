package car_model.API_Compare_Spring_Quarkus.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.String.format;

@Component
public class JwtTokenUtil {
    private final String jwtSecret = "It should work eventually";

    public String generateAccessToken(String username, String authorities) {
        return Jwts.builder()
                .setSubject(format("%s", username))
                .claim("role",authorities) // Add a custom claim with the key "role"
                .setIssuer("Car_Compare")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000)) // 2h
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject().split(",")[0];
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
}