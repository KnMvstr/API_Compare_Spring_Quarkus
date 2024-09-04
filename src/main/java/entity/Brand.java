package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.slugify.Slugify;
import dto.BrandDTO;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Brand extends PanacheEntity {
    /* Using the recommended hibernate with Panache I can reduce the boiler plate.
    That way I don't have to specify a Long id as it is done by the extends.
     */
    private String name;

    @JsonIgnore
    private String slug;

    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER)
    // FetchEager is mandatory
    private List<Model> models = new ArrayList<>();


    // Automatically generate the slug before persisting or updating
    @PrePersist
    @PreUpdate
    public void generateSlug() {
        Slugify slugify = Slugify.builder().build();
        this.slug = slugify.slugify(this.name);
    }

    /* Other specificity of using Panache it is recommended to get the usual DDD service layer method inside the entity.

    In Quarkus DDD with service layer our method point to the repositories which extends interface PanacheRepositoryBase.
    PanacheRepositoryBase is an interface provided by Quarkus Hibernate ORM Panache set of methods for performing common
     database operations on entities, same as JpaRepository in use in Springboot repository.

     When using abstract class PanacheEntity we don't need service layer as method are declared in the entity.
     Plus we don't need repository layer as the abstract PanacheEntity extends PanacheEntityBase
     which contains same operation as PanacheRepositoryBase point to AbstractJPaOperation containing all the necessary.
     */

    public static List<BrandDTO> getAllBrands() {
        List<Brand> brands = Brand.listAll(Sort.by("name"));
        return brands.stream().map(Brand::toDTO).collect(Collectors.toList());
    }

    public static BrandDTO getBrandById(Long id) {
        Brand brand = Brand.findById(id);
        if (brand == null) {
            throw new EntityNotFoundException("Could not find brand with id: " + id);
        }
        return toDTO(brand);
    }

    public static BrandDTO persist(BrandDTO brandDTO, Long id) {
        Brand brand;
        if (id != null) {
            brand = Brand.findById(id);
            if (brand == null) {
                throw new EntityNotFoundException("Could not find brand with id: " + id);
            }
        } else {
            brand = new Brand();
        }
        brand.setName(brandDTO.getName());
        Brand.persist(brand);
        return toDTO(brand);
    }

    public static void delete(Long id) {
        Brand brand = Brand.findById(id);
        if (brand == null) {
            throw new EntityNotFoundException("Could not find brand with id: " + id);
        }
        Brand.deleteById(brand);
    }

    public static Brand fromDTO(BrandDTO dto) {
        Brand brand = new Brand();
        brand.name = dto.getName();
        return brand;
    }

    public void updateFromDTO(BrandDTO dto) {
        this.name = dto.getName();
    }

    public static BrandDTO toDTO(Brand brand) {
        BrandDTO dto = new BrandDTO();
        dto.setId(brand.id);
        dto.setName(brand.name);
        return dto;
    }
}