package repository;

import entity.Color;
import entity.Model;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class ColorRepository implements PanacheRepositoryBase<Color, Long> {

    // Method to find multiple colors based on a list of IDs
    // Useful to pot multiple color on a new model
    public List<Color> findByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return list("id in ?1", ids);
    }
}