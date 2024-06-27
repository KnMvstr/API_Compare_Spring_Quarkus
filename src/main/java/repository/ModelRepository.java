package repository;

import entity.Model;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ModelRepository implements PanacheRepositoryBase<Model, Long> {
}
