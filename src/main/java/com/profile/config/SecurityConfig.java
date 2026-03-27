package com.profile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.*;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> {}) // ✅ ENABLE CORS
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                
                // ✅ VERY IMPORTANT (allow preflight)
                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()

                .requestMatchers("/api/career-vision/**").authenticated()
                .requestMatchers("/api/certificates/**").authenticated()
                .requestMatchers("/api/education/**").authenticated()
                .requestMatchers("/api/experience/**").authenticated()
                .requestMatchers("/api/profile/**").authenticated()
                .requestMatchers("/api/skill/**").authenticated()
                .requestMatchers("/api/social/**").authenticated()
                .anyRequest().authenticated()
            );

        return http.build();
    }

    // ✅ GLOBAL CORS CONFIG (REQUIRED)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}