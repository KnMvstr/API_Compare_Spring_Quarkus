package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "roles")
@EqualsAndHashCode(callSuper=false)
@Data
public class Role extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<User> users = new ArrayList<>();
}
