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
     * Used for authentication and profile management.
     */
    Optional<Doctor> findByEmail(String email);
    
    /**
     * Find doctor by phone number.
     * Useful for contact verification and emergency scenarios.
     */
    Optional<Doctor> findByPhoneNumber(String phoneNumber);
    
    /**
     * Find doctors by specialization with case-insensitive matching.
     * Essential for patient search and appointment booking.
     */
    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);
    
    /**
     * Find doctors by name with case-insensitive partial matching.
     * Supports partial name matching for doctor search functionality.
     */
    @Query("SELECT d FROM Doctor d WHERE LOWER(CONCAT(d.firstName, ' ', d.lastName)) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Doctor> findByNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Find available doctors by specialization and time range.
     * Critical for appointment booking system.
     */
    @Query("SELECT DISTINCT d FROM Doctor d " +
           "JOIN d.availabilitySlots da " +
           "WHERE LOWER(d.specialization) LIKE LOWER(CONCAT('%', :specialization, '%')) " +
           "AND da.date = CAST(:dateTime AS DATE) " +
           "AND da.startTime <= CAST(:dateTime AS TIME) " +
           "AND da.endTime >= CAST(:dateTime AS TIME) " +
           "AND da.availabilityType = 'AVAILABLE' " +
           "AND d.isActive = true")
    List<Doctor> findAvailableDoctorsBySpecializationAndTime(
        @Param("specialization") String specialization,
        @Param("dateTime") LocalDateTime dateTime
    );
    
    /**
     * Find doctors with upcoming appointments.
     * Useful for workload management and scheduling.
     */
    @Query("SELECT DISTINCT d FROM Doctor d " +
           "JOIN d.appointments a " +
           "WHERE a.appointmentDate >= CURRENT_DATE " +
           "AND a.status IN ('SCHEDULED', 'CONFIRMED') " +
           "ORDER BY d.firstName, d.lastName")
    List<Doctor> findDoctorsWithUpcomingAppointments();
    
    /**
     * Find doctors ordered by their appointment count.
     * Helps in workload balancing and performance metrics.
     */
    @Query("SELECT d FROM Doctor d " +
           "LEFT JOIN d.appointments a " +
           "WHERE a.appointmentDate BETWEEN :startDate AND :endDate " +
           "GROUP BY d.doctorId " +
           "ORDER BY COUNT(a.appointmentId) DESC")
    List<Doctor> findDoctorsOrderedByAppointmentCount(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    /**
     * Find active doctors only.
     * Standard filter for active practitioners.
     */
    List<Doctor> findByIsActiveTrue();
    
    /**
     * Find doctors by years of experience range.
     * Useful for filtering based on experience requirements.
     */
    List<Doctor> findByYearsExperienceBetween(Integer minYears, Integer maxYears);
    
    /**
     * Count total active doctors.
     * Used for administrative reporting and statistics.
     */
    @Query("SELECT COUNT(d) FROM Doctor d WHERE d.isActive = true")
    Long countActiveDoctors();
    
    /**
     * Find doctors by office location.
     * Helps patients find nearby healthcare providers.
     */
    List<Doctor> findByOfficeLocationContainingIgnoreCase(String location);
    
    /**
     * Check if email exists for another doctor (not the given ID).
     * Used during updates to prevent duplicate emails.
     */
    boolean existsByEmailAndDoctorIdNot(String email, Long doctorId);
    
    /**
     * Check if phone number exists for another doctor (not the given ID).
     * Used during updates to prevent duplicate phone numbers.
     */
    boolean existsByPhoneNumberAndDoctorIdNot(String phoneNumber, Long doctorId);
    
    /**
     * Find all unique specializations.
     * Useful for dropdown lists and filtering.
     */
    @Query("SELECT DISTINCT d.specialization FROM Doctor d WHERE d.specialization IS NOT NULL ORDER BY d.specialization")
    List<String> findAllSpecializations();
    
    /**
     * Count total doctors (alternative method name).
     * Used for administrative reporting.
     */
    @Query("SELECT COUNT(d) FROM Doctor d")
    Long countTotalDoctors();
    
    /**
     * Count doctors by specialization.
     * Returns a map-like structure for reporting.
     */
    @Query("SELECT d.specialization, COUNT(d) FROM Doctor d GROUP BY d.specialization")
    List<Object[]> countDoctorsBySpecialization();
}
