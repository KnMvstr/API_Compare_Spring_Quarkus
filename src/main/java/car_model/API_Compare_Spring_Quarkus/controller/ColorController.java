package car_model.API_Compare_Spring_Quarkus.controller;

import car_model.API_Compare_Spring_Quarkus.dto.ColorDTO;
import car_model.API_Compare_Spring_Quarkus.entity.Color;
import car_model.API_Compare_Spring_Quarkus.json_views.JsonViews;
import car_model.API_Compare_Spring_Quarkus.service.ColorService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/colors")
@RequiredArgsConstructor
public class ColorController {

    private final ColorService colorService;

    @GetMapping("/{id}")
    @JsonView(JsonViews.ColorPlus.class)
    public Color getColorById(@PathVariable Long id) {
        return colorService.getColorById(id);
    }

    @GetMapping
    @JsonView(JsonViews.Color.class)
    List<Color> getAllColors() {
        return colorService.getAllColors();
    }

    @PostMapping(path = "/create")
    @JsonView(JsonViews.ColorAdd.class)
    public Color createColor(@Validated @RequestBody ColorDTO colorDTO) {
        return colorService.persist(colorDTO, null);
    }

    @PutMapping(path = "/edit/{id}")
    @JsonView(JsonViews.ColorAdd.class)
    public ResponseEntity<Color> updateColor(@PathVariable Long id, @RequestBody ColorDTO colorDTO) {
        Color colorUpdated = colorService.persist(colorDTO, id);
        return new ResponseEntity<>(colorUpdated, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        colorService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}