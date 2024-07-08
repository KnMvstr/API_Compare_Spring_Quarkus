package car_model.API_Compare_Spring_Quarkus.controller;

import car_model.API_Compare_Spring_Quarkus.dto.ModelDTO;
import car_model.API_Compare_Spring_Quarkus.entity.Model;
import car_model.API_Compare_Spring_Quarkus.json_views.JsonViews;
import car_model.API_Compare_Spring_Quarkus.service.ModelService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/models")
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;

    @GetMapping("/{id}")
    @JsonView(JsonViews.ModelPlus.class)
    public Model getModelById(@PathVariable Long id) {

        return modelService.getModelById(id);
    }

    @GetMapping
    @JsonView(JsonViews.Model.class)
    List<Model> getAllModels() {
        return modelService.getAllModels();
    }

    @PostMapping(path = "/create")
    @JsonView(JsonViews.Model.class)
    public Model createModel(@Validated @RequestBody ModelDTO modelDTO) {

        return modelService.persist(modelDTO, null);
    }

    @PutMapping(path = "/edit/{id}")
    @JsonView(JsonViews.Model.class)
    public ResponseEntity<Model> updateModel(@PathVariable Long id, @Valid @RequestBody ModelDTO modelDTO) {
        Model modelUpdated = modelService.persist(modelDTO, id);
        return new ResponseEntity<>(modelUpdated, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        modelService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}