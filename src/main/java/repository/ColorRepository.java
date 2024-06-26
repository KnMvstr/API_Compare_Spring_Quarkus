package repository;

import entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.awt.*;

@ApplicationScoped
public class ColorRepository implements PanacheRepositoryBase<Color, Long> {
}