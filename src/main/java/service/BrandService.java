package service;

import dto.BrandDTO;
import entity.Brand;
import entity.Model;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import repository.BrandRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class BrandService {
    BrandRepository brandRepository;

    public List<BrandDTO> getAllBrands() {
        List<Brand> brands = brandRepository.listAll(Sort.by("name"));
        return brands.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public BrandDTO getBrandById(Long id) {
        Brand brand = brandRepository.findById(id);
        if (brand == null) {
            throw new EntityNotFoundException("Could not find brand with id: " + id);
        }
        return toDTO(brand);
    }

    public BrandDTO persist(BrandDTO brandDTO, Long id) {
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
        return toDTO(brand);
    }

    public void delete(Long id) {
        Brand brand = brandRepository.findById(id);
        if (brand == null) {
            throw new EntityNotFoundException("Could not find brand with id: " + id);
        }
        brandRepository.delete(brand);
    }

    // In our configuration it is mandatory to convert our plain object into a DTO.
    // We avoid the LazyLoading exception throw due to our relations between entities.
    private BrandDTO toDTO(Brand brand) {
        BrandDTO dto = new BrandDTO();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        // The brand must return the list of model linked
        List<String> modelNames = brand.getModels().stream()
                .map(Model::getName)
                .collect(Collectors.toList());
        dto.setModelNames(modelNames);
        return dto;
    }
}