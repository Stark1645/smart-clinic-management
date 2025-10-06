package com.example.clinic.controller;

import com.example.clinic.model.Doctor;
import com.example.clinic.service.DoctorService;
import com.example.clinic.service.TokenService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final TokenService tokenService;

    public DoctorController(DoctorService doctorService, TokenService tokenService) {
        this.doctorService = doctorService;
        this.tokenService = tokenService;
    }

    // ✅ Endpoint for doctor availability
    // Example: GET /api/doctors/1/availability?date=2025-10-06
    @GetMapping("/{id}/availability")
    public ResponseEntity<?> getAvailability(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Token validation
        String emailFromToken = tokenService.parseEmail(authHeader);

        if (emailFromToken == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid or missing token"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("doctorId", id);
        response.put("date", date);
        response.put("requestedBy", emailFromToken);
        response.put("availableSlots", doctorService.getAvailableTimeSlots(id, date));

        return ResponseEntity.ok(response);
    }

    // ✅ Simple login endpoint for doctors
    // Example: GET /api/doctors/login?email=dr.a@example.com&password=pass123
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        boolean valid = doctorService.validateLogin(email, password);
        if (!valid) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

        String token = tokenService.generateToken(email);
        return ResponseEntity.ok(Map.of(
                "token", token,
                "email", email,
                "message", "Login successful"
        ));
    }
}
