package service;

import dto.RoleDTO;
import dto.UserAuthDTO;
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

//    public UserDTO findbyEmail(String email) {
//        Optional<User> user = userRepository.findByEmail(email);
//        if (user.isEmpty()) {
//            throw new EntityNotFoundException("Could not find user with corresponding email : " + email);
//        }
//        return toDTO(user.get());
//    }

//    public UserDTO findbyUsername(String userName) {
//        Optional<User> user = userRepository.findByUserName(userName);
//        if (user.isEmpty()) {
//            throw new EntityNotFoundException("Could not find user with corresponding username : " + userName);
//        }
//        return toDTO(user.get());
//    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new EntityNotFoundException("Could not find user with id: " + id);
        }
        return toDTO(user);
    }

    public UserDTO createUser(UserAuthDTO userDTO) {
        User user = new User();
        // Check if username already exists
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + userDTO.getUsername());
        }
        // Set username
        user.setUsername(userDTO.getUsername());

        // Set email from DTO
        user.setEmail(userDTO.getEmail());

        // User password is automatically hashed by Quarkus Security
        // Hashing manually using BcryptUtil as Quarkus didn't hash automatically
        String hashedPassword = BcryptUtil.bcryptHash(userDTO.getPassword());
        user.setPassword(hashedPassword);

        // Fetch default role 'USER', assuming it always exists.
        // The role has been pre-loaded during application start-up.
        Role defaultRole = roleRepository.findByName("USER");
        if (defaultRole == null) {
            // If the 'USER' role doesn't exist, we create it
            defaultRole = new Role();
            defaultRole.setName("USER");
            roleRepository.persist(defaultRole);
        }
        user.setRole(defaultRole);

        userRepository.persist(user);
        return toDTO(user);
    }


    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new EntityNotFoundException("Could not find user with id: " + id);
        }
        user.setUsername(userDTO.getUsername());
        // Hash the password before setting it
        String hashedPassword = BcryptUtil.bcryptHash(userDTO.getPassword());
        user.setPassword(hashedPassword);

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

    public UserDTO authenticate(UserAuthDTO userAuthDTO) {
        User user = userRepository.findByUsername(userAuthDTO.getUsername()).orElse(null);
        if (user == null || !BcryptUtil.matches(userAuthDTO.getPassword(), user.getPassword())) {
            return null;
        }
        return toDTO(user);
    }


    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
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