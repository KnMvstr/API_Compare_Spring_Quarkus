package repository;

import entity.CarType;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CarTypeRepository implements PanacheRepositoryBase<CarType, Long> {
}
