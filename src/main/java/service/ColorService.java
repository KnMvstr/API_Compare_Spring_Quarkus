package service;

import dto.ColorDTO;
import entity.Color;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import repository.ColorRepository;
import java.util.List;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class ColorService {

    ColorRepository colorRepository;


    public List<Color> getAllColors() {
        return colorRepository.listAll(Sort.by("name"));
    }

    public Color getColorById(Long id) {
        Color color = colorRepository.findById(id);
        if (color == null) {
            throw new EntityNotFoundException("Could not find color with id: " + id);
        }
        return color;
    }

    public Color persist(ColorDTO colorDTO, Long id) {
        Color color;
        if (id != null) {
            color = colorRepository.findById(id);
            if (color == null) {
                throw new EntityNotFoundException("Could not find color with id: " + id);
            }
        } else {
            color = new Color();
        }
        color.setName(colorDTO.getName());
        color.setRvbRef(colorDTO.getRvbRef());
        color.setHexRef(colorDTO.getHexRef());
        colorRepository.persist(color);
        return color;
    }

    public void delete(Long id) {
        Color color = colorRepository.findById(id);
        if (color == null) {
            throw new EntityNotFoundException("Could not find color with id: " + id);
        }
        colorRepository.delete(color);
    }
}
