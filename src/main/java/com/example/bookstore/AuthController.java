package com.example.bookstore;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final OtpService otpService;
    // CRITICAL: Inject UserDetailsService to fetch user details for Spring Security
    private final UserDetailsService userDetailsService; 

    // Constructor Injection
    // NOTE: You must ensure UserDetailsService is configured as a Spring Bean
    public AuthController(OtpService otpService, UserDetailsService userDetailsService) {
        this.otpService = otpService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
        String phone = request.get("phoneNumber");
        otpService.sendOtp(phone);
        return ResponseEntity.ok(Map.of("status", "sent"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String phone = request.get("phoneNumber");
        String code = request.get("code");
        boolean valid = otpService.verifyOtp(phone, code);
        
        if (valid) {
            
            // 🚨 STEP 1: Load User Details (using phone as the username)
            UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
            
            // 🚨 STEP 2: Create Authentication Token (No password needed for OTP)
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            
            // 🚨 STEP 3: Establish the Session (THIS IS THE FIX)
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Return success with the redirect URL for client-side navigation
            return ResponseEntity.ok(Map.of(
                "status", "approved", 
                "redirectUrl", "/bookstore" 
            ));
        } else {
            // Return 401 Unauthorized status for security failure
            return ResponseEntity.status(401).body(Map.of("status", "denied", "message", "Invalid or expired OTP."));
        }
    }
}