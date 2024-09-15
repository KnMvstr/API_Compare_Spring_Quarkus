package resource;

import authentication.AuthResponse;
import authentication.token.TokenService;
import dto.UserAuthDTO;
import dto.UserDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserService userService;

    @Inject
    TokenService tokenService;

    /**
     * Endpoint to authenticate a user and generate a JWT.
     * @return A response containing the JWT or an error message.
     */
    @POST
    @Path("/login")
    public Response authenticateUser(UserAuthDTO userAuthDTO) {
        try {
            UserDTO user = userService.authenticate(userAuthDTO);
            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
            }
            String token = tokenService.generateToken(user);
            return Response.ok(new AuthResponse(token)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred during authentication").build();
        }
    }

    /**
     * Endpoint for user registration.
     * @param user UserDTO containing the new user's details.
     * @return A response indicating success or failure.
     */
    @POST
    @Path("/register")
    public Response registerUser(UserAuthDTO user) {
        try {
            UserDTO newUser = userService.createUser(user);
            return Response.status(Response.Status.CREATED).entity(newUser).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Server error during registration").build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout(String token) {
        if (tokenService.invalidateToken(token)) {
            return Response.ok().build(); // Logout successful
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid token").build();
        }
    }
}
