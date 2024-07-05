package authentication;

import dto.UserDTO;
import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jwt.JwtClaims;
import java.util.Collections;
import java.util.logging.Logger;

@RequestScoped
public class TokenService {

    // Logger to log messages for this class
    public final static Logger LOGGER = Logger.getLogger(TokenService.class.getSimpleName());

    /**
     * Generates a JWT token for a user based on provided details.
     *
     * @param user User object that includes email, username, and role information
     * @return a string representing the JWT token
     */
    public String generateToken(UserDTO user) {
        try {
            // Create a new JWT claims object
            JwtClaims jwtClaims = new JwtClaims();

            // Set the issuer of the token
            jwtClaims.setIssuer("Car_Compare");

            // Set a unique identifier for the token
            jwtClaims.setJwtId("ash93");

            // Set the subject of the token
            jwtClaims.setSubject("authentication");

            // Add custom claims based on the User object
            jwtClaims.setClaim(Claims.upn.name(), user.getEmail());
            jwtClaims.setClaim(Claims.preferred_username.name(), user.getUsername());
            // Retrieve the role from the User entity
            jwtClaims.setClaim(Claims.groups.name(), Collections.singletonList(user.getRole()));

            // Define the audience for this token
            jwtClaims.setAudience("using-jwt");

            // Set token expiration time to 60 minute from now
            jwtClaims.setExpirationTimeMinutesInTheFuture(60);

            // Generate the token string from the claims
            String token = TokenUtils.generateTokenString(jwtClaims);

            // Log the generated token
            LOGGER.info("TOKEN generated: " + token);

            // Return the generated token
            return token;

        } catch (Exception e) {
            // Log the exception stack trace in case of an error
            LOGGER.info("Error generating token");
            throw new RuntimeException(e);
        }
    }
}