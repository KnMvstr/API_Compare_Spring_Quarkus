package service;

import dto.RoleDTO;
import dto.UserDTO;
import entity.Role;
import entity.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import repository.RoleRepository;
import repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;

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

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        // Check if username already exists
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + userDTO.getUsername());
        }
        user.setUsername(userDTO.getUsername());
        // User password is automatically hashed by Quarkus Security
        // In our case it didn't get hashed without calling manual Hashing with BcryptUtil
        String hashedPassword = BcryptUtil.bcryptHash(userDTO.getPassword());
        user.setPassword(hashedPassword);
        // Fetch existing role by name
        Role existingRole = roleRepository.findByName(userDTO.getRole().getName());
        // Handle role assignment based on existingRole
        if (existingRole != null) {
            user.setRole(existingRole);
        } else {
            // Handle non-existent role (throw exception or create new role with caution)
            throw new IllegalArgumentException("Invalid role provided. Please specify 'ADMIN' or 'USER'");
        }
        userRepository.persist(user);
        return toDTO(user);
    }


    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new EntityNotFoundException("Could not find user with id: " + id);
        }
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        // Fetch existing role by name
        Role existingRole = roleRepository.findByName(userDTO.getRole().getName());
        if (existingRole != null) {
            user.setRole(existingRole);
        } else {
            throw new IllegalArgumentException(
                    "Could not find role with name: " + userDTO.getRole().getName() + ". Please specify 'ADMIN' or 'USER'");
        }
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
        dto.setRole(toRoleDTO(user.getRole()));
        return dto;
    }

    private RoleDTO toRoleDTO(Role role) {
        if (role == null) {
            return null;
        }
        RoleDTO dto = new RoleDTO();
        dto.setName(role.getName());
        return dto;
    }

    private Role fromRoleDTO(RoleDTO dto) {
        if (dto == null) {
            return null;
        }
        Role role = new Role();
        role.setName(dto.getName());
        return role;
    }
}