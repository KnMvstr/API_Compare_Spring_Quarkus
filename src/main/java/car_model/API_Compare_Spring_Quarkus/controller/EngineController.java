package car_model.API_Compare_Spring_Quarkus.controller;

import car_model.API_Compare_Spring_Quarkus.dto.EngineDTO;
import car_model.API_Compare_Spring_Quarkus.entity.Engine;
import car_model.API_Compare_Spring_Quarkus.json_views.JsonViews;
import car_model.API_Compare_Spring_Quarkus.service.EngineService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/engines")
@RequiredArgsConstructor
public class EngineController {

    private final EngineService engineService;

    @GetMapping("/{id}")
    @Operation(summary= "Get a engine by ID")
    @JsonView(JsonViews.EnginePlus.class)
    public Engine getEngineById(@PathVariable Long id) {
        return engineService.getEngineById(id);
    }

    @GetMapping
    @Operation(summary= "Get all engines")
    @JsonView(JsonViews.Engine.class)
    List<Engine> getAllEngines() {
        return engineService.getAllEngines();
    }

    @PostMapping(path = "/create")
    @Operation(summary= "Create engine", description= "Create one that don't exist yet")
    @JsonView(JsonViews.Engine.class)
    public Engine createEngine(@Validated @RequestBody EngineDTO engineDTO) {
        return engineService.persist(engineDTO, null);
    }

    @PutMapping(path = "/edit/{id}")
    @Operation(summary= "Modify a engine", description= "Change the name only")
    @JsonView(JsonViews.Engine.class)
    public ResponseEntity<Engine> updateEngine(@PathVariable Long id, @RequestBody EngineDTO engineDTO) {
        Engine engineUpdated = engineService.persist(engineDTO, id);
        return new ResponseEntity<>(engineUpdated, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Operation(summary= "Delete a engine", description= "Deletion is definitive")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        engineService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}