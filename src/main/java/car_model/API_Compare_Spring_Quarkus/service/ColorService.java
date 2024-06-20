package car_model.API_Compare_Spring_Quarkus.service;

import car_model.API_Compare_Spring_Quarkus.dto.ColorDTO;
import car_model.API_Compare_Spring_Quarkus.entity.Color;
import car_model.API_Compare_Spring_Quarkus.exception.NotFoundException;
import car_model.API_Compare_Spring_Quarkus.repository.ColorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ColorService {
    private final ColorRepository colorRepository;

    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    public Color getColorById(Long id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find color with id: " + id));
    }

    public Color persist(ColorDTO colorDTO, Long id) {
        Color color;
        if (id != null) {
            // Attempt to find the color by ID
            color = colorRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Could not find color with id: " + id));
            // Update the existing color's fields
            color.setName(colorDTO.getName());
            // Add any other fields from the DTO you wish to update
        } else {
            // If no ID is provided, create a new color
            color = new Color();
            color.setName(colorDTO.getName());
        }
        // Save the updated color or the new color
        return colorRepository.saveAndFlush(color);
    }

    public void delete(Long id) {
        if (!colorRepository.existsById(id)) {
            throw new NotFoundException("Could not find color with id: " + id);
        }
        colorRepository.deleteById(id);
    }
}