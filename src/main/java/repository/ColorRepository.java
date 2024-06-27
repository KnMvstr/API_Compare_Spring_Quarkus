package repository;

import entity.Color;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ColorRepository implements PanacheRepositoryBase<Color, Long> {
}