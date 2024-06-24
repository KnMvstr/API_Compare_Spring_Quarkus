package car_model.API_Compare_Spring_Quarkus.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig  {
    private BCryptPasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .securityMatcher("/api/v1/**")
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                                auth
                                        .requestMatchers("/api/v1/**").permitAll()
//                                        .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
//                                        .requestMatchers(HttpMethod.POST, "/api/v1/**").permitAll()
//                                        .requestMatchers(HttpMethod.PUT, "/api/v1/**").permitAll()
//                                        .requestMatchers(HttpMethod.DELETE, "/api/v1/**").permitAll()
                )
        ;
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)
            throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return http
                .authenticationProvider(authProvider)
                .getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }
}