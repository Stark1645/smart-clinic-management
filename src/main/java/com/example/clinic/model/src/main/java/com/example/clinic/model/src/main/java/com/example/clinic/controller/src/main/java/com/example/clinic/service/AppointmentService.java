package com.example.clinic.service;

import com.example.clinic.model.Appointment;
import com.example.clinic.model.Doctor;
import com.example.clinic.model.Patient;
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    // ✅ Book a new appointment
    public Appointment bookAppointment(Appointment appointment) {
        Optional<Doctor> doctor = doctorRepository.findById(appointment.getDoctor().getId());
        Optional<Patient> patient = patientRepository.findById(appointment.getPatient().getId());

        if (doctor.isEmpty() || patient.isEmpty()) {
            throw new IllegalArgumentException("Doctor or Patient not found");
        }

        appointment.setDoctor(doctor.get());
        appointment.setPatient(patient.get());

        return appointmentRepository.save(appointment);
    }

    // ✅ Get all appointments for a doctor on a given date
    public List<Appointment> getAppointmentsForDoctorOnDate(Long doctorId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctorId, startOfDay, endOfDay);
    }
}
