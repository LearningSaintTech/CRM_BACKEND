package com.example.springsocial.controller;

import com.example.springsocial.exception.BadRequestException;
import com.example.springsocial.model.AuthProvider;
import com.example.springsocial.model.ChangeNewPassword;
import com.example.springsocial.model.Role;
import com.example.springsocial.model.User;
import com.example.springsocial.model.VerifyOtpRequest;
import com.example.springsocial.payload.ApiResponse;
import com.example.springsocial.payload.AuthResponse;
import com.example.springsocial.payload.LoginRequest;
import com.example.springsocial.payload.ResetPasswordRequest;
import com.example.springsocial.payload.SignUpRequest;
import com.example.springsocial.repository.RoleRepository;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.security.TokenProvider;
import com.example.springsocial.services.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;
    
    @Autowired
    private EmailService emailService;
    private Map<String, String> otpStorage = new HashMap<>(); // Temporary storage for OTPs

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        User user = new User();
        
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setProvider(AuthProvider.local);
        user.setStatus("inactive");

         Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new BadRequestException("User Role not set."));
        user.setRoles(Collections.singletonList(userRole));
        String otp = String.valueOf((int)(Math.random() * 9000) + 1000); // Generate a random 4-digit OTP
        user.setOtp(otp);
        User result = userRepository.save(user);
        
        

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();
       
        try {
            emailService.sendRegistrationEmail(user.getEmail(), user.getName(), otp);
        } catch (Exception e) {
            System.err.println("Failed to send registration email: " + e.getMessage());
        }
        
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }
    
    @GetMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        System.out.println("Received email: " + email);
        System.out.println("Received OTP: " + otp);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found."));
        System.out.println("Stored OTP: " + user.getOtp());

        if (user.getOtp().equals(otp)) {
            user.setEmailVerified(true);
            user.setOtp(null); // Clear the OTP after successful verification
            user.setStatus("active");
            userRepository.save(user);
            return ResponseEntity.ok(new ApiResponse(true, "Email verified successfully."));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid OTP."));
        }
    }

    
    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestParam String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found."));

        // Generate a new OTP
        String newOtp = String.valueOf((int) (Math.random() * 9000) + 1000); // 4-digit OTP
        user.setOtp(newOtp);
        userRepository.save(user);

        try {
            // Resend the OTP email
            emailService.sendRegistrationEmail(user.getEmail(), user.getName(), newOtp);
        } catch (Exception e) {
            System.err.println("Failed to resend OTP email: " + e.getMessage());
            return ResponseEntity.status(500).body(new ApiResponse(false, "Failed to resend OTP. Please try again."));
        }

        return ResponseEntity.ok(new ApiResponse(true, "OTP resent successfully"));
    }
    
//    @GetMapping("/verify-otp")
//    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
//        System.out.println("Received email: " + email);
//        System.out.println("Received OTP: " + otp);
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new BadRequestException("User not found."));
//        System.out.println("Stored OTP: " + user.getOtp());
//
//        if (user.getOtp().equals(otp)) {
//            user.setEmailVerified(true);
//            user.setOtp(null); // Clear the OTP after successful verification
//            user.setStatus("active");
//            userRepository.save(user);
//            return ResponseEntity.ok(new ApiResponse(true, "Email verified successfully."));
//        } else {
//            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid OTP."));
//        }
//    }

    
 // Endpoint to request password reset
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found."));

        // Generate OTP
        String otp = String.valueOf((int)(Math.random() * 9000) + 1000);
        user.setResetOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(10)); // OTP valid for 10 minutes
        userRepository.save(user);

        // Send OTP email
        emailService.sendPasswordResetEmail(email, otp);
        System.out.println("otp is>>>>"+otp);
        
        return ResponseEntity.ok(new ApiResponse(true, "OTP sent to your email."));
    }
    

    
    @PostMapping("/verify-reset-otp")
    public ResponseEntity<?> verifyResetOtp(@RequestBody VerifyOtpRequest request) {
    	System.out.println("request>>>>>>>>>>"+request);
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("User not found."));
        System.out.println("helloooo"+user);

        System.out.println("showing otp 1 is:"+user.getResetOtp());
        System.out.println("showing otp 2 is:"+request.getOtp());
        // Check if OTP is valid and not expired
        if (user.getResetOtp().equals(request.getOtp()) && user.getOtpExpiry().isAfter(LocalDateTime.now())) {
        	System.out.println("helllooooo");
            return ResponseEntity.ok(new ApiResponse(true, "OTP verified. You can now reset your password."));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid or expired OTP."));
        }
    }

    
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword1(@RequestBody ChangeNewPassword passwordRequest) {
        User user = userRepository.findByEmail(passwordRequest.getEmail())
                .orElseThrow(() -> new BadRequestException("User not found."));
        
        
        System.out.println("Received OTP: " + passwordRequest.getOtp());
        System.out.println("Stored OTP: " + user.getResetOtp());

        // Check if OTP is valid and not expired
        if (user.getResetOtp().equals(passwordRequest.getOtp()) && user.getOtpExpiry().isAfter(LocalDateTime.now())) {
            // Reset password
            user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            user.setResetOtp(null); // Clear the OTP after successful reset
            user.setOtpExpiry(null);
            userRepository.save(user);

            return ResponseEntity.ok(new ApiResponse(true, "Password reset successfully."));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid or expired OTP."));
        }
    }     

}
