package service;

import dto.ColorDTO;
import entity.Color;
import entity.Model;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import repository.ColorRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class ColorService {

    ColorRepository colorRepository;


    public List<ColorDTO> getAllColors() {
        List<Color> colors = colorRepository.listAll(Sort.by("name"));
        return colors.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ColorDTO getColorById(Long id) {
        Color color = colorRepository.findById(id);
        if (color == null) {
            throw new EntityNotFoundException("Could not find color with id: " + id);
        }
        return toDTO(color);
    }

    public ColorDTO persist(ColorDTO colorDTO, Long id) {
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
        return toDTO(color);
    }

    public void delete(Long id) {
        Color color = colorRepository.findById(id);
        if (color == null) {
            throw new EntityNotFoundException("Could not find color with id: " + id);
        }
        colorRepository.delete(color);
    }

    // In our configuration it is mandatory to convert our plain object into a DTO.
    // We avoid the LazyLoading exception throw due to our relations between entities.
    private ColorDTO toDTO(Color color) {
        ColorDTO dto = new ColorDTO();
        dto.setId(color.getId());
        dto.setName(color.getName());
        dto.setRvbRef(color.getRvbRef());
        dto.setHexRef(color.getHexRef());

        //fetch model names if necessary
        dto.setModelNames(color.getModels().stream()
                .map(Model::getName)
                .collect(Collectors.toList()));

        return dto;
    }
}
