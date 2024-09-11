package repository;

import entity.CarType;
import entity.Color;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class CarTypeRepository implements PanacheRepositoryBase<CarType, Long> {

    // Method to find multiple cartypes based on a list of IDs
    // Useful to pot multiple cartypes on a new model
    public List<CarType> findByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return list("id in ?1", ids);
    }
}
