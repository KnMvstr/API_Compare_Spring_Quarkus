package service;

import dto.BrandDTO;
import dto.CarTypeDTO;
import entity.Brand;
import entity.CarType;
import entity.CarType;
import entity.Model;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import repository.CarTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class CarTypeService {
    CarTypeRepository carTypeRepository;

    public List<CarTypeDTO> getAllCarTypes() {
        List<CarType> carTypes = carTypeRepository.listAll(Sort.by("name"));
        return carTypes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CarTypeDTO getCarTypeById(Long id) {
        CarType carType = carTypeRepository.findById(id);
        if (carType == null) {
            throw new EntityNotFoundException("Could not find carType with id: " + id);
        }
        return toDTO(carType);
    }


    public CarTypeDTO persist(CarTypeDTO carTypeDTO, Long id) {
        CarType carType;
        if (id != null) {
            carType = carTypeRepository.findById(id);
            if (carType == null) {
                throw new EntityNotFoundException("Could not find carType with id: " + id);
            }
        } else {
            carType = new CarType();
        }
        carType.setName(carTypeDTO.getName());
        carTypeRepository.persist(carType);
        return toDTO(carType);
    }

    public void delete(Long id) {
        CarType carType = carTypeRepository.findById(id);
        if (carType == null) {
            throw new EntityNotFoundException("Could not find carType with id: " + id);
        }
        carTypeRepository.delete(carType);
    }

    private CarTypeDTO toDTO(CarType carType) {
        CarTypeDTO dto = new CarTypeDTO();
        dto.setId(carType.getId());
        dto.setName(carType.getName());
        // The brand must return the list of model linked
        List<String> modelNames = carType.getModels().stream()
                .map(Model::getName)
                .collect(Collectors.toList());
        dto.setModelNames(modelNames);
        return dto;
    }

}
