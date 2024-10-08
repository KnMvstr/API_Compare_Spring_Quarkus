package car_model.API_Compare_Spring_Quarkus.controller;

import car_model.API_Compare_Spring_Quarkus.dto.UserLoginDTO;
import car_model.API_Compare_Spring_Quarkus.dto.UserPostDTO;
import car_model.API_Compare_Spring_Quarkus.entity.User;
import car_model.API_Compare_Spring_Quarkus.security.JwtAuthenticationService;
import car_model.API_Compare_Spring_Quarkus.security.JwtTokenResponse;
import car_model.API_Compare_Spring_Quarkus.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication")
@RequiredArgsConstructor
public class SecurityRestController {
    private final UserService userService;
    private final JwtAuthenticationService jwtAuthenticationService;


    @PostMapping("/login")
    @Operation(summary= "Login to your account", description= "Specify your username and password")
    public ResponseEntity<JwtTokenResponse> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return jwtAuthenticationService.authenticate(userLoginDTO);
    }

    @PostMapping("/register")
    @Operation(summary= "Create your personal account", description= "Specify your username and password")
    public ResponseEntity<?> register(@RequestBody @Valid UserPostDTO userDTO) {
        try {
            User user = userService.create(userDTO);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}