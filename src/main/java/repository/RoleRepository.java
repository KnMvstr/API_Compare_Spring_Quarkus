package repository;

import entity.Role;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoleRepository implements PanacheRepositoryBase<Role, Long> {
    public Role findByName(String name) {
        return find("name", name).firstResult();
    }
}
