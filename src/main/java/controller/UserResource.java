package controller;

import entity.User;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import repository.UserRepository;

import java.util.List;

@Transactional
@Path("/api/v2/users")
public class UserResource {

    @Inject
    UserRepository userRepository;

    @GET
    @Path("/{id}")
    public User getUserById(@PathParam("id") Long id) {
        return userRepository.findById(id);
    }

    @GET
    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }
}
