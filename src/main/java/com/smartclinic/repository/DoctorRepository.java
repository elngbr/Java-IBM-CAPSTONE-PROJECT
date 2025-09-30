package com.smartclinic.repository;

import com.smartclinic.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Doctor entity operations.
 * Provides CRUD operations and custom queries for doctor management.
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    /**
     * Find doctor by email address.
     * Used for authentication and unique email validation.
     */
    Optional<Doctor> findByEmail(String email);
    
    /**
     * Find doctor by phone number.
     * Used for contact verification and lookup.
     */
    Optional<Doctor> findByPhone(String phone);
    
    /**
     * Find doctors by specialization.
     * Core functionality for patient search and appointment booking.
     */
    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);
    
    /**
     * Find doctors by name (case-insensitive search).
     * Supports partial name matching for doctor search functionality.
     */
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Doctor> findByNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Find available doctors by specialization and time range.
     * Critical for appointment booking system.
     */
    @Query("SELECT DISTINCT d FROM Doctor d " +
           "JOIN d.availableTimes da " +
           "WHERE LOWER(d.specialization) LIKE LOWER(CONCAT('%', :specialization, '%')) " +
           "AND da.availableDate = DATE(:dateTime) " +
           "AND da.startTime <= TIME(:dateTime) " +
           "AND da.endTime >= TIME(:dateTime) " +
           "AND da.isAvailable = true")
    List<Doctor> findAvailableDoctorsBySpecializationAndTime(
        @Param("specialization") String specialization, 
        @Param("dateTime") LocalDateTime dateTime);
    
    /**
     * Find doctors with upcoming appointments.
     * Used for doctor dashboard and schedule management.
     */
    @Query("SELECT DISTINCT d FROM Doctor d " +
           "JOIN d.appointments a " +
           "WHERE a.appointmentTime >= CURRENT_TIMESTAMP " +
           "ORDER BY d.name")
    List<Doctor> findDoctorsWithUpcomingAppointments();
    
    /**
     * Find doctor with most appointments in a date range.
     * Used for performance reporting and workload analysis.
     */
    @Query("SELECT d FROM Doctor d " +
           "JOIN d.appointments a " +
           "WHERE a.appointmentTime BETWEEN :startDate AND :endDate " +
           "GROUP BY d.id " +
           "ORDER BY COUNT(a) DESC")
    List<Doctor> findDoctorsOrderedByAppointmentCount(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate);
    
    /**
     * Check if email already exists (excluding current doctor).
     * Used for email uniqueness validation during updates.
     */
    @Query("SELECT COUNT(d) > 0 FROM Doctor d WHERE d.email = :email AND d.id != :doctorId")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("doctorId") Long doctorId);
    
    /**
     * Check if phone number already exists (excluding current doctor).
     * Used for phone uniqueness validation during updates.
     */
    @Query("SELECT COUNT(d) > 0 FROM Doctor d WHERE d.phone = :phone AND d.id != :doctorId")
    boolean existsByPhoneAndIdNot(@Param("phone") String phone, @Param("doctorId") Long doctorId);
    
    /**
     * Find all specializations available in the system.
     * Used for filter dropdowns and specialization management.
     */
    @Query("SELECT DISTINCT d.specialization FROM Doctor d ORDER BY d.specialization")
    List<String> findAllSpecializations();
    
    /**
     * Count total doctors.
     * Used for dashboard statistics and reporting.
     */
    @Query("SELECT COUNT(d) FROM Doctor d")
    Long countTotalDoctors();
    
    /**
     * Count doctors by specialization.
     * Used for specialization distribution analysis.
     */
    @Query("SELECT d.specialization, COUNT(d) FROM Doctor d GROUP BY d.specialization")
    List<Object[]> countDoctorsBySpecialization();
}
