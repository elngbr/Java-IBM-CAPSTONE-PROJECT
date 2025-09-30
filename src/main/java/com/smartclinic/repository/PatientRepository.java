package com.smartclinic.repository;

import com.smartclinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Patient entity operations.
 * Provides CRUD operations and custom queries for patient management.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    /**
     * Find patient by email address.
     * Used for authentication and profile management.
     */
    Optional<Patient> findByEmail(String email);
    
    /**
     * Find patient by phone number.
     * Useful for contact verification and lookup.
     */
    Optional<Patient> findByPhoneNumber(String phoneNumber);
    
    /**
     * Find patients by name with case-insensitive partial matching.
     * Supports partial name search for patient lookup.
     */
    @Query("SELECT p FROM Patient p WHERE LOWER(CONCAT(p.firstName, ' ', p.lastName)) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Patient> findByNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Find patients by gender.
     * Used for gender-specific medical analysis and reporting.
     */
    List<Patient> findByGender(String gender);
    
    /**
     * Find patients by blood type.
     * Critical for emergency medical situations.
     */
    List<Patient> findByBloodType(String bloodType);
    
    /**
     * Find active patients only.
     * Standard filter for active patient records.
     */
    List<Patient> findByIsActiveTrue();
    
    /**
     * Count total active patients.
     * Used for administrative reporting and statistics.
     */
    @Query("SELECT COUNT(p) FROM Patient p WHERE p.isActive = true")
    Long countActivePatients();
    
    /**
     * Find patients with upcoming appointments.
     * Useful for patient engagement and scheduling.
     */
    @Query("SELECT DISTINCT p FROM Patient p " +
           "JOIN p.appointments a " +
           "WHERE a.appointmentDate >= CURRENT_DATE " +
           "AND a.status IN ('SCHEDULED', 'CONFIRMED') " +
           "ORDER BY p.firstName, p.lastName")
    List<Patient> findPatientsWithUpcomingAppointments();
    
    /**
     * Find patients by emergency contact.
     * Used for emergency contact searches.
     */
    List<Patient> findByEmergencyContactNameContainingIgnoreCase(String contactName);
}
