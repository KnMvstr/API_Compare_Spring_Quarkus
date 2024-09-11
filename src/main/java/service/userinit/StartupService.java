package service.userinit;

import entity.Role;
import entity.User;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StartupService {
    // Method to populate Roles and Users using Quarkus StartupEvent
    @Transactional
    public void loadUsersAndRoles(@Observes StartupEvent evt) {
        //First check if roles table is empty in DB
        if (Role.count() == 0) {
            // Create role Admin
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.persist();

            // Create role User
            Role userRole = new Role();
            userRole.setName("USER");
            userRole.persist();

            // Create an admin
            User admin = new User();
            admin.setUsername("kenzi");
            admin.setEmail("kenzi@hotmail.fr");
            admin.setPassword("$2a$10$wjv7.1y6eTCS6TJ1c0BR9.jj0Wz2vesfFTjo/CERqXM5kfZ9iM0Sq"); // Bcrypt hashed Password
            admin.setRole(adminRole); // link the associated role
            admin.persist();

            // Create a user
            User user = new User();
            user.setUsername("gracia");
            user.setEmail("gracia@hotmail.fr");
            user.setPassword("$2a$10$Udr/2mKcfNWU1pNHgrad9eolYicPzq5ZRw.OwRX3Cq361HfnUbGa."); // Bcrypt hashed Password
            user.setRole(userRole); // link the associated role
            user.persist();
        }
    }
}