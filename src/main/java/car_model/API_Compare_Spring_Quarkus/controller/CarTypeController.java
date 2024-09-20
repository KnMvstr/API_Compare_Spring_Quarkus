package car_model.API_Compare_Spring_Quarkus.controller;

import car_model.API_Compare_Spring_Quarkus.dto.CarTypeDTO;
import car_model.API_Compare_Spring_Quarkus.entity.CarType;
import car_model.API_Compare_Spring_Quarkus.json_views.JsonViews;
import car_model.API_Compare_Spring_Quarkus.service.CarTypeService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car_types")
@RequiredArgsConstructor
public class CarTypeController {
    private final CarTypeService carTypeService;

    @GetMapping("/{id}")
    @Operation(summary= "Get a car type by ID")
    @JsonView(JsonViews.CarTypePlus.class)
    public CarType getCarTypeById(@PathVariable Long id) {
        return carTypeService.getCarTypeById(id);
    }

    @GetMapping
    @Operation(summary= "Get all car types")
    @JsonView(JsonViews.CarType.class)
    List<CarType> getAllCarTypes() {
        return carTypeService.getAllCarTypes();
    }

    @PostMapping(path = "/create")
    @Operation(summary= "Create car type", description= "Create one that don't exist yet")
    @JsonView(JsonViews.CarType.class)
    public CarType createCarType(@Validated @RequestBody CarTypeDTO carTypeDTO) {
        return carTypeService.persist(carTypeDTO, null);
    }

    @PutMapping(path = "/edit/{id}")
    @Operation(summary= "Modify a brand", description= "Change the name only")
    @JsonView(JsonViews.CarType.class)
    public ResponseEntity<CarType> updateCarType(@PathVariable Long id, @RequestBody CarTypeDTO carTypeDTO) {
        CarType carTypeUpdated = carTypeService.persist(carTypeDTO, id);
        return new ResponseEntity<>(carTypeUpdated, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Operation(summary= "Delete a brand", description= "Deletion is definitive")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        carTypeService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}