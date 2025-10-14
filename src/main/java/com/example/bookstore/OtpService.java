package com.example.bookstore;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OtpService {
    private final OtpRepository otpRepository;

    public OtpService(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    private String generateOtp() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
    }

    public void sendOtp(String phoneNumber) {
        String code = generateOtp();
        Otp otp = new Otp();
        otp.setPhoneNumber(phoneNumber);
        otp.setCode(code);
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otp);

        // For now: print OTP in console (later integrate Twilio/MSG91 SMS)
        System.out.println("OTP for " + phoneNumber + " = " + code);
    }

    public boolean verifyOtp(String phoneNumber, String code) {
        Optional<Otp> opt = otpRepository.findTopByPhoneNumberOrderByExpiresAtDesc(phoneNumber);
        if (opt.isEmpty()) return false;

        Otp otp = opt.get();
        if (otp.isUsed() || otp.getExpiresAt().isBefore(LocalDateTime.now())) return false;

        if (otp.getCode().equals(code)) {
            otp.setUsed(true);
            otpRepository.save(otp);
            return true;
        }
        return false;
    }
}
