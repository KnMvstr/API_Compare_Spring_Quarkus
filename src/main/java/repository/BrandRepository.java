package repository;

import entity.Brand;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BrandRepository implements PanacheRepositoryBase<Brand, Long> {
}
