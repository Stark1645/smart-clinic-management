package com.example.clinic.service;

import com.example.clinic.model.Doctor;
import com.example.clinic.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // ✅ Get doctor by ID
    public Optional<Doctor> findById(Long id) {
        return doctorRepository.findById(id);
    }

    // ✅ Get doctor by email
    public Optional<Doctor> findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    // ✅ Validate login credentials
    public boolean validateLogin(String email, String password) {
        return doctorRepository.findByEmail(email)
                .map(doc -> doc.getPassword().equals(password))
                .orElse(false);
    }

    // ✅ Get available time slots for a doctor on a specific date
    public List<LocalDateTime> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        if (doctorOpt.isEmpty()) return Collections.emptyList();

        Doctor doctor = doctorOpt.get();
        List<LocalTime> times = doctor.getAvailableTimes();

        if (times == null || times.isEmpty()) return Collections.emptyList();

        return times.stream()
                .map(time -> LocalDateTime.of(date, time))
                .collect(Collectors.toList());
    }
}
