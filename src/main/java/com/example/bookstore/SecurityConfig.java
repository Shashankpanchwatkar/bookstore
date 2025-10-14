package com.example.bookstore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Authorize all HTTP requests
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Allow ALL requests without authentication
            )
            
            // 2. Disable CSRF protection (since there's no auth/session state to protect)
            .csrf(csrf -> csrf.disable()); 

        // Optional: If you still see a default login page, sometimes you need to explicitly 
        // disable HTTP Basic authentication which might be enabled by default.
        // http.httpBasic(httpBasic -> httpBasic.disable());
        // http.formLogin(form -> form.disable());
        
        return http.build();
    }
}