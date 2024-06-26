package entity;

import io.vertx.ext.auth.authorization.impl.PermissionBasedAuthorizationConverter;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Permission;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    private String name;

    @Convert(converter = PermissionBasedAuthorizationConverter.class)
    private Set<Permission> permissions = new HashSet<>();

}
