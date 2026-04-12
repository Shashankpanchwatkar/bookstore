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
    
    private final UserDetailsService userDetailsService; 

    
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
            
            UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
            
            
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
         
            return ResponseEntity.ok(Map.of(
                "status", "approved", 
                "redirectUrl", "/bookstore" 
            ));
        } else {
           
            return ResponseEntity.status(401).body(Map.of("status", "denied", "message", "Invalid or expired OTP."));
        }
    }
}
