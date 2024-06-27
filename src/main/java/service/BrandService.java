package service;

import dto.BrandDTO;
import entity.Brand;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import repository.BrandRepository;
import java.util.List;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class BrandService {
    BrandRepository brandRepository;

    public List<Brand> getAllBrands() {
        return brandRepository.listAll(Sort.by("name"));
    }

    public Brand getBrandById(Long id) {
        Brand brand = brandRepository.findById(id);
        if (brand == null) {
            throw new EntityNotFoundException("Could not find brand with id: " + id);
        }
        return brand;
    }

    public Brand persist(BrandDTO brandDTO, Long id) {
        Brand brand;
        if (id != null) {
            brand = brandRepository.findById(id);
            if (brand == null) {
                throw new EntityNotFoundException("Could not find brand with id: " + id);
            }
        } else {
            brand = new Brand();
        }
        brand.setName(brandDTO.getName());
        brandRepository.persist(brand);
        return brand;
    }

    public void delete(Long id) {
        Brand brand = brandRepository.findById(id);
        if (brand == null) {
            throw new EntityNotFoundException("Could not find brand with id: " + id);
        }
        brandRepository.delete(brand);
    }
}