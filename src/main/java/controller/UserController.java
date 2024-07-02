package controller;

import dto.UserDTO;
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
public class UserController {

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
    public Response createUser(@Valid UserDTO user) {
        UserDTO newUser = userService.persist(user);
        return Response.status(Response.Status.CREATED).entity(newUser).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        userService.delete(id);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}