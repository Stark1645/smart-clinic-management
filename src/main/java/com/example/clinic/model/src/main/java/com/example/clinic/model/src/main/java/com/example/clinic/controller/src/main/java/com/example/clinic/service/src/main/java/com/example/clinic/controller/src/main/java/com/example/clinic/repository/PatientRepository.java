package com.example.clinic.repository;

import com.example.clinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // ✅ Find a patient by email
    Optional<Patient> findByEmail(String email);

    // ✅ Find a patient by email OR phone
    Optional<Patient> findByEmailOrPhone(String email, String phone);
}
