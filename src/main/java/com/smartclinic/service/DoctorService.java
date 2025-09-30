package com.smartclinic.service;

import com.smartclinic.model.Doctor;
import com.smartclinic.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Doctor entity operations.
 * Contains business logic for doctor management, authentication, and search functionality.
 */
@Service
@Transactional
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Create a new doctor.
     * Validates uniqueness and encrypts password before saving.
     */
    public Doctor createDoctor(Doctor doctor) {
        // Check email uniqueness
        if (doctorRepository.findByEmail(doctor.getEmail()).isPresent()) {
            throw new RuntimeException("Doctor with email " + doctor.getEmail() + " already exists");
        }
        
        // Check phone uniqueness
        if (doctorRepository.findByPhone(doctor.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Doctor with phone " + doctor.getPhoneNumber() + " already exists");
        }
        
        // Encode password
        doctor.setPasswordHash(passwordEncoder.encode(doctor.getPasswordHash()));
        
        return doctorRepository.save(doctor);
    }

    /**
     * Update an existing doctor.
     * Validates uniqueness for email and phone (excluding current doctor).
     */
    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Doctor doctor = getDoctorById(id);
        
        // Check email uniqueness (excluding current doctor)
        if (!doctor.getEmail().equals(doctorDetails.getEmail()) && 
            doctorRepository.existsByEmailAndIdNot(doctorDetails.getEmail(), id)) {
            throw new RuntimeException("Email " + doctorDetails.getEmail() + " is already in use");
        }
        
        // Check phone uniqueness (excluding current doctor)
        if (!doctor.getPhoneNumber().equals(doctorDetails.getPhoneNumber()) && 
            doctorRepository.existsByPhoneAndIdNot(doctorDetails.getPhoneNumber(), id)) {
            throw new RuntimeException("Phone " + doctorDetails.getPhoneNumber() + " is already in use");
        }
        
        // Update fields
        doctor.setFirstName(doctorDetails.getFirstName());
        doctor.setLastName(doctorDetails.getLastName());
        doctor.setEmail(doctorDetails.getEmail());
        doctor.setPhoneNumber(doctorDetails.getPhoneNumber());
        doctor.setSpecialization(doctorDetails.getSpecialization());
        
        // Only update password if provided
        if (doctorDetails.getPasswordHash() != null && !doctorDetails.getPasswordHash().isEmpty()) {
            doctor.setPasswordHash(passwordEncoder.encode(doctorDetails.getPasswordHash()));
        }
        
        return doctorRepository.save(doctor);
    }

    /**
     * Get doctor by ID.
     * Throws exception if not found.
     */
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }

    /**
     * Get doctor by email.
     * Used for authentication and login.
     */
    public Optional<Doctor> getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    /**
     * Get all doctors.
     * Used for admin management and public directory.
     */
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    /**
     * Search doctors by name.
     * Supports partial name matching for patient search.
     */
    public List<Doctor> searchDoctorsByName(String name) {
        return doctorRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Search doctors by specialization.
     * Core functionality for appointment booking.
     */
    public List<Doctor> searchDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecializationContainingIgnoreCase(specialization);
    }

    /**
     * Find available doctors by specialization and time.
     * Critical for appointment booking system.
     */
    public List<Doctor> findAvailableDoctors(String specialization, LocalDateTime dateTime) {
        return doctorRepository.findAvailableDoctorsBySpecializationAndTime(specialization, dateTime);
    }

    /**
     * Get all specializations.
     * Used for filter dropdowns and specialization management.
     */
    public List<String> getAllSpecializations() {
        return doctorRepository.findAllSpecializations();
    }

    /**
     * Delete doctor by ID.
     * Admin functionality for doctor management.
     */
    public void deleteDoctor(Long id) {
        Doctor doctor = getDoctorById(id);
        doctorRepository.delete(doctor);
    }

    /**
     * Authenticate doctor login.
     * Verifies email and password for doctor portal access.
     */
    public Optional<Doctor> authenticateDoctor(String email, String password) {
        Optional<Doctor> doctorOpt = doctorRepository.findByEmail(email);
        
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            if (passwordEncoder.matches(password, doctor.getPasswordHash())) {
                return Optional.of(doctor);
            }
        }
        
        return Optional.empty();
    }

    /**
     * Get doctors with upcoming appointments.
     * Used for dashboard and schedule management.
     */
    public List<Doctor> getDoctorsWithUpcomingAppointments() {
        return doctorRepository.findDoctorsWithUpcomingAppointments();
    }

    /**
     * Get doctor statistics.
     * Used for reporting and analytics.
     */
    public Long getTotalDoctorCount() {
        return doctorRepository.countTotalDoctors();
    }

    /**
     * Get doctor count by specialization.
     * Used for specialization distribution analysis.
     */
    public List<Object[]> getDoctorCountBySpecialization() {
        return doctorRepository.countDoctorsBySpecialization();
    }

    /**
     * Change doctor password.
     * Security functionality for password updates.
     */
    public void changePassword(Long doctorId, String oldPassword, String newPassword) {
        Doctor doctor = getDoctorById(doctorId);
        
        if (!passwordEncoder.matches(oldPassword, doctor.getPasswordHash())) {
            throw new RuntimeException("Current password is incorrect");
        }
        
        doctor.setPasswordHash(passwordEncoder.encode(newPassword));
        doctorRepository.save(doctor);
    }
}
