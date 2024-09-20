package car_model.API_Compare_Spring_Quarkus.controller;

import car_model.API_Compare_Spring_Quarkus.dto.ModelDTO;
import car_model.API_Compare_Spring_Quarkus.entity.Model;
import car_model.API_Compare_Spring_Quarkus.json_views.JsonViews;
import car_model.API_Compare_Spring_Quarkus.service.ModelService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary= "Get a model by ID")
    @JsonView(JsonViews.ModelPlus.class)
    public Model getModelById(@PathVariable Long id) {

        return modelService.getModelById(id);
    }

    @GetMapping
    @Operation(summary= "Get all models")
    @JsonView(JsonViews.Model.class)
    List<Model> getAllModels() {
        return modelService.getAllModels();
    }

    @PostMapping(path = "/create")
    @Operation(summary= "Create model", description= "Create one that don't exist yet")
    @JsonView(JsonViews.Model.class)
    public Model createModel(@Validated @RequestBody ModelDTO modelDTO) {

        return modelService.persist(modelDTO, null);
    }

    @PutMapping(path = "/edit/{id}")
    @Operation(summary= "Modify a model", description= "Change the name only")
    @JsonView(JsonViews.Model.class)
    public ResponseEntity<Model> updateModel(@PathVariable Long id, @Valid @RequestBody ModelDTO modelDTO) {
        Model modelUpdated = modelService.persist(modelDTO, id);
        return new ResponseEntity<>(modelUpdated, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Operation(summary= "Delete a model", description= "Deletion is definitive")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        modelService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}