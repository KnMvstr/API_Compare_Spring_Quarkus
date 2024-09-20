package car_model.API_Compare_Spring_Quarkus.controller;

import car_model.API_Compare_Spring_Quarkus.dto.BrandDTO;
import car_model.API_Compare_Spring_Quarkus.entity.Brand;
import car_model.API_Compare_Spring_Quarkus.json_views.JsonViews;
import car_model.API_Compare_Spring_Quarkus.service.BrandService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping ("/{id}")
    @Operation(summary= "Get a brand by ID")
    @JsonView(JsonViews.BrandPlus.class)
    public Brand getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id);
    }

    @GetMapping
    @Operation(summary= "Get all brands")
    @JsonView(JsonViews.Brand.class)
    List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @PostMapping(path = "/create")
    @Operation(summary= "Create brand", description= "Create one that don't exist yet")
    @JsonView(JsonViews.Brand.class)
    public Brand createBrand(@Validated  @RequestBody BrandDTO brandDTO) {
        return brandService.persist(brandDTO, null);
    }

    @PutMapping(path = "/edit/{id}")
    @Operation(summary= "Modify a brand", description= "Change the name only")
    @JsonView(JsonViews.Brand.class)
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @RequestBody BrandDTO brandDTO) {
        Brand brandUpdated = brandService.persist(brandDTO, id);
        return new ResponseEntity<>(brandUpdated, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Operation(summary= "Delete a brand", description= "Deletion is definitive")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        brandService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}