package com.example.bookstore;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections; // Required for Collections.emptyList()

// NOTE: You will need to create and import your actual UserRepository and User entity 
// once you replace the mock logic.

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    // @Autowired private UserRepository userRepository; // Uncomment and implement later

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        
        // --- REAL IMPLEMENTATION GOES HERE ---
        // 1. Find user entity by phone number in your database
        // User user = userRepository.findByPhoneNumber(phoneNumber)
        //     .orElseThrow(() -> new UsernameNotFoundException("User not found: " + phoneNumber));

        // 2. Return a Spring Security User object
        // For a mock, temporary fix (for testing the login flow):
        
        // Ensure the phone number is non-null before trying to return UserDetails
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
             throw new UsernameNotFoundException("Phone number cannot be empty.");
        }
        
        // Mock return (This needs to be replaced with real user lookup and authority assignment)
        return new org.springframework.security.core.userdetails.User(
            phoneNumber, 
            "", // No password needed for OTP login
            Collections.emptyList() // List of roles/authorities (must not be null)
        );
    }
}