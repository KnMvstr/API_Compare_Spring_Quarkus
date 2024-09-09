package car_model.API_Compare_Spring_Quarkus.service;

import car_model.API_Compare_Spring_Quarkus.dto.UserPostDTO;
import car_model.API_Compare_Spring_Quarkus.entity.User;
import car_model.API_Compare_Spring_Quarkus.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                userGrantedAuthorities(user)
        );
    }


    private Collection<? extends GrantedAuthority> userGrantedAuthorities(User user) {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRoles()));
    }

    public User create(UserPostDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("This username is already in use");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername().toLowerCase());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setRoles(userDTO.getRoles() != null ? userDTO.getRoles() : "ROLE_USER");

        return userRepository.saveAndFlush(user);
    }
}