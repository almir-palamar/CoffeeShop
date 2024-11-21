package com.example.coffeeshop.config;

import com.example.coffeeshop.filters.JwtAuthFilter;
import com.example.coffeeshop.filters.ToGoPendingOrdersFilter;
import com.example.coffeeshop.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final ToGoPendingOrdersFilter toGoPendingOrdersFilter;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeConfig ->
                        authorizeConfig
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/api/v1/auth/login").permitAll()
                                .requestMatchers("/api/v1/auth/register").permitAll()
                                .requestMatchers("/api/v1/auth/logout").permitAll()
                                .requestMatchers("/api/v1/orders/**").permitAll()
                                .requestMatchers("/actuator/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/coffees", "/api/v1/coffees/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/coffees/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/coffees/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/coffees/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(toGoPendingOrdersFilter, JwtAuthFilter.class)
                .logout(logout -> logout.logoutUrl("/api/v1/auth/logout")
                        .logoutSuccessUrl("/api/v1/auth/login").permitAll())
                .build();
    }

}
