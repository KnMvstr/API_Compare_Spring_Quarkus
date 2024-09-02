package repository;

import entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, Long> {

    public Optional<User> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }

    public Optional<User> findByEmail(String email) {return find("email", email).firstResultOptional();}
    public Optional<User> findByUserName(String userName) {return find("username", userName).firstResultOptional();}
    public boolean existsByUsername(String username) {
        return find("username", username).count() > 0;
    }
}
