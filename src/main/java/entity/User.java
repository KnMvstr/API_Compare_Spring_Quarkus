package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "\"user\"")
@UserDefinition
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Username
    // Indicates the field used for the username.
    @Column(name = "username", length = 15, nullable = false, unique = true)
    private String username;

    @Email
    private String email;

    @Password
    // Indicates the field used for the password. By default, it uses bcrypt-hashed passwords.
    // You can configure it to use plain text or custom passwords. @Password(PasswordType.CLEAR)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @Roles
    public String getRoleName() {
        return role.getName();
    }
}