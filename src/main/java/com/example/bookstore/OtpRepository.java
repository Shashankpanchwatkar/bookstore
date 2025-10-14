package com.example.bookstore;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findTopByPhoneNumberOrderByExpiresAtDesc(String phoneNumber);
}