package resource;

import dto.UserAuthDTO;
import dto.UserDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.UserService;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v2/users")
public class UserResource  {

    @Inject
    UserService userService;

    @GET
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        UserDTO user = userService.getUserById(id);
        return Response.ok(user).build();
    }

    @POST
    @Path("/create")
    public Response createUser(@Valid UserAuthDTO user) {
        UserDTO newUser = userService.createUser(user);
        return Response.status(Response.Status.CREATED).entity(newUser).build();
    }

    @RolesAllowed({"ADMIN"})
    @PUT
    @Path("/edit/{id}")
    public Response updateUser(@PathParam("id") Long id, @Valid UserDTO user){
        UserDTO updatedUser = userService.updateUser(id, user);
        return Response.status(Response.Status.ACCEPTED).entity(updatedUser).build();
    }

    @RolesAllowed({"ADMIN"})
    @DELETE
    @Path("/delete/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        userService.delete(id);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}