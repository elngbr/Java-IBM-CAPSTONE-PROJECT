package com.smartclinic.controller;

import com.smartclinic.model.Doctor;
import com.smartclinic.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Doctor entity operations.
 * Provides CRUD endpoints and doctor-specific functionality.
 * Required for doctor management in the Smart Clinic Management System.
 */
@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    /**
     * Create a new doctor.
     * POST /api/doctors
     */
    @PostMapping
    public ResponseEntity<?> createDoctor(@Valid @RequestBody Doctor doctor) {
        try {
            Doctor createdDoctor = doctorService.createDoctor(doctor);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get all doctors.
     * GET /api/doctors
     */
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    /**
     * Get doctor by ID.
     * GET /api/doctors/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) {
        try {
            Doctor doctor = doctorService.getDoctorById(id);
            return ResponseEntity.ok(doctor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update doctor by ID.
     * PUT /api/doctors/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Long id, @Valid @RequestBody Doctor doctorDetails) {
        try {
            Doctor updatedDoctor = doctorService.updateDoctor(id, doctorDetails);
            return ResponseEntity.ok(updatedDoctor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Delete doctor by ID.
     * DELETE /api/doctors/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        try {
            doctorService.deleteDoctor(id);
            return ResponseEntity.ok().body(Map.of("message", "Doctor deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Search doctors by name.
     * GET /api/doctors/search/name?name={name}
     */
    @GetMapping("/search/name")
    public ResponseEntity<List<Doctor>> searchDoctorsByName(@RequestParam String name) {
        List<Doctor> doctors = doctorService.searchDoctorsByName(name);
        return ResponseEntity.ok(doctors);
    }

    /**
     * Search doctors by specialization.
     * GET /api/doctors/search/specialization?specialization={specialization}
     */
    @GetMapping("/search/specialization")
    public ResponseEntity<List<Doctor>> searchDoctorsBySpecialization(@RequestParam String specialization) {
        List<Doctor> doctors = doctorService.searchDoctorsBySpecialization(specialization);
        return ResponseEntity.ok(doctors);
    }

    /**
     * Find available doctors by specialization and time.
     * GET /api/doctors/available?specialization={specialization}&dateTime={dateTime}
     */
    @GetMapping("/available")
    public ResponseEntity<List<Doctor>> findAvailableDoctors(
            @RequestParam String specialization,
            @RequestParam String dateTime) {
        try {
            LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime);
            List<Doctor> availableDoctors = doctorService.findAvailableDoctors(specialization, parsedDateTime);
            return ResponseEntity.ok(availableDoctors);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get all specializations.
     * GET /api/doctors/specializations
     */
    @GetMapping("/specializations")
    public ResponseEntity<List<String>> getAllSpecializations() {
        List<String> specializations = doctorService.getAllSpecializations();
        return ResponseEntity.ok(specializations);
    }

    /**
     * Get doctors with upcoming appointments.
     * GET /api/doctors/with-appointments
     */
    @GetMapping("/with-appointments")
    public ResponseEntity<List<Doctor>> getDoctorsWithUpcomingAppointments() {
        List<Doctor> doctors = doctorService.getDoctorsWithUpcomingAppointments();
        return ResponseEntity.ok(doctors);
    }

    /**
     * Get doctor statistics.
     * GET /api/doctors/statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getDoctorStatistics() {
        Long totalCount = doctorService.getTotalDoctorCount();
        List<Object[]> countBySpecialization = doctorService.getDoctorCountBySpecialization();
        
        Map<String, Object> statistics = Map.of(
            "totalDoctors", totalCount,
            "countBySpecialization", countBySpecialization
        );
        
        return ResponseEntity.ok(statistics);
    }

    /**
     * Change doctor password.
     * POST /api/doctors/{id}/change-password
     */
    @PostMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(
            @PathVariable Long id,
            @RequestBody Map<String, String> passwordData) {
        try {
            String oldPassword = passwordData.get("oldPassword");
            String newPassword = passwordData.get("newPassword");
            
            if (oldPassword == null || newPassword == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Old password and new password are required"));
            }
            
            doctorService.changePassword(id, oldPassword, newPassword);
            return ResponseEntity.ok().body(Map.of("message", "Password changed successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get doctor by email.
     * GET /api/doctors/by-email?email={email}
     */
    @GetMapping("/by-email")
    public ResponseEntity<?> getDoctorByEmail(@RequestParam String email) {
        return doctorService.getDoctorByEmail(email)
                .map(doctor -> ResponseEntity.ok(doctor))
                .orElse(ResponseEntity.notFound().build());
    }
}
