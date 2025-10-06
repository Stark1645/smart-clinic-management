package com.example.clinic.controller;

import com.example.clinic.model.Prescription;
import com.example.clinic.service.PrescriptionService;
import com.example.clinic.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final TokenService tokenService;

    public PrescriptionController(PrescriptionService prescriptionService, TokenService tokenService) {
        this.prescriptionService = prescriptionService;
        this.tokenService = tokenService;
    }

    // ✅ POST endpoint to create a new prescription
    @PostMapping
    public ResponseEntity<?> createPrescription(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @Valid @RequestBody Prescription prescription) {

        String doctorEmail = tokenService.parseEmail(authHeader);

        if (doctorEmail == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid or missing token"));
        }

        prescription.setPrescribedBy(doctorEmail);
        Prescription savedPrescription = prescriptionService.save(prescription);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "prescriptionId", savedPrescription.getId(),
                "prescribedBy", doctorEmail
        ));
    }
}
