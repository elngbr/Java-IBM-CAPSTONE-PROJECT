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
     * Used for authentication and unique email validation.
     */
    Optional<Patient> findByEmail(String email);
    
    /**
     * Find patient by phone number.
     * Used for patient lookup and contact verification.
     */
    Optional<Patient> findByPhone(String phone);
    
    /**
     * Find patients by name (case-insensitive search).
     * Supports partial name matching for patient search functionality.
     */
    @Query("SELECT p FROM Patient p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Patient> findByNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Find patients by age range.
     * Useful for demographic analysis and age-specific treatments.
     */
    @Query("SELECT p FROM Patient p WHERE p.age BETWEEN :minAge AND :maxAge")
    List<Patient> findByAgeBetween(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);
    
    /**
     * Find patients by gender.
     * Used for gender-specific medical analysis and reporting.
     */
    List<Patient> findByGender(String gender);
    
    /**
     * Check if email already exists (excluding current patient).
     * Used for email uniqueness validation during updates.
     */
    @Query("SELECT COUNT(p) > 0 FROM Patient p WHERE p.email = :email AND p.id != :patientId")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("patientId") Long patientId);
    
    /**
     * Check if phone number already exists (excluding current patient).
     * Used for phone uniqueness validation during updates.
     */
    @Query("SELECT COUNT(p) > 0 FROM Patient p WHERE p.phone = :phone AND p.id != :patientId")
    boolean existsByPhoneAndIdNot(@Param("phone") String phone, @Param("patientId") Long patientId);
    
    /**
     * Find active patients (those with recent appointments).
     * Used for active patient reporting and engagement analysis.
     */
    @Query("SELECT DISTINCT p FROM Patient p " +
           "JOIN p.appointments a " +
           "WHERE a.appointmentTime >= CURRENT_DATE - 90")
    List<Patient> findActivePatients();
    
    /**
     * Count total patients.
     * Used for dashboard statistics and reporting.
     */
    @Query("SELECT COUNT(p) FROM Patient p")
    Long countTotalPatients();
}
