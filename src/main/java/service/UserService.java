package service;

import dto.UserDTO;
import entity.User;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class UserService {
    UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.listAll(Sort.by("username"));
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new EntityNotFoundException("Could not find user with id: " + id);
        }
        return toDTO(user);
    }

    public UserDTO persist(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        userRepository.persist(user);
        return toDTO(user);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new EntityNotFoundException("Could not find user with id: " + id);
        }
        userRepository.delete(user);
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }
}