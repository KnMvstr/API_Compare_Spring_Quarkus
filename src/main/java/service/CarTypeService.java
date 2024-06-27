package service;

import dto.CarTypeDTO;
import entity.CarType;
import entity.CarType;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import repository.CarTypeRepository;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class CarTypeService {
    CarTypeRepository carTypeRepository;

    public List<CarType> getAllCarTypes() {
        return carTypeRepository.listAll(Sort.by("name"));
    }

    public CarType getCarTypeById(Long id) {
        CarType carType = carTypeRepository.findById(id);
        if (carType == null) {
            throw new EntityNotFoundException("Could not find carType with id: " + id);
        }
        return carType;
    }


    public CarType persist(CarTypeDTO carTypeDTO, Long id) {
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
        return carType;
    }

    public void delete(Long id) {
        CarType carType = carTypeRepository.findById(id);
        if (carType == null) {
            throw new EntityNotFoundException("Could not find carType with id: " + id);
        }
        carTypeRepository.delete(carType);
    }

}
