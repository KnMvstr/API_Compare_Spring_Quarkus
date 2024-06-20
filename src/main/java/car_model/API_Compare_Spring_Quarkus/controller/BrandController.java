package car_model.API_Compare_Spring_Quarkus.controller;

import car_model.API_Compare_Spring_Quarkus.dto.BrandDTO;
import car_model.API_Compare_Spring_Quarkus.entity.Brand;
import car_model.API_Compare_Spring_Quarkus.json_views.JsonViews;
import car_model.API_Compare_Spring_Quarkus.service.BrandService;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(JsonViews.BrandPlus.class)
    public Brand getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id);
    }

    @GetMapping
    @JsonView(JsonViews.Brand.class)
    List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @PostMapping(path = "/create")
    @JsonView(JsonViews.Brand.class)
    public Brand createBrand(@Validated @RequestBody BrandDTO brandDTO) {
        return brandService.persist(brandDTO, null);
    }

    @PutMapping(path = "/edit/{id}")
    @JsonView(JsonViews.Brand.class)
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @RequestBody BrandDTO brandDTO) {
        Brand brandUpdated = brandService.persist(brandDTO, id);
        return new ResponseEntity<>(brandUpdated, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        brandService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}