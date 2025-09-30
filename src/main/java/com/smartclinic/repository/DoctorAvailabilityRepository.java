package com.smartclinic.repository;

import com.smartclinic.model.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for DoctorAvailability entity operations.
 * Provides CRUD operations and custom queries for doctor availability management.
 */
@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {
    
    /**
     * Find availability by doctor ID.
     * Core functionality for displaying doctor's schedule.
     */
    List<DoctorAvailability> findByDoctorIdOrderByAvailableDateAscStartTimeAsc(Long doctorId);
    
    /**
     * Find available slots for a doctor on a specific date.
     * Used for appointment booking system.
     */
    @Query("SELECT da FROM DoctorAvailability da WHERE da.doctor.id = :doctorId " +
           "AND da.availableDate = :date " +
           "AND da.isAvailable = true " +
           "ORDER BY da.startTime ASC")
    List<DoctorAvailability> findAvailableSlotsByDoctorAndDate(
        @Param("doctorId") Long doctorId,
        @Param("date") LocalDate date);
    
    /**
     * Find availability by doctor, date and time range.
     * Used to check if doctor is available for a specific appointment time.
     */
    @Query("SELECT da FROM DoctorAvailability da WHERE da.doctor.id = :doctorId " +
           "AND da.availableDate = :date " +
           "AND da.startTime <= :time " +
           "AND da.endTime >= :time " +
           "AND da.isAvailable = true")
    Optional<DoctorAvailability> findByDoctorIdAndDateAndTime(
        @Param("doctorId") Long doctorId,
        @Param("date") LocalDate date,
        @Param("time") LocalTime time);
    
    /**
     * Find upcoming availability for a doctor.
     * Used in doctor portal to show upcoming available slots.
     */
    @Query("SELECT da FROM DoctorAvailability da WHERE da.doctor.id = :doctorId " +
           "AND da.availableDate >= CURRENT_DATE " +
           "ORDER BY da.availableDate ASC, da.startTime ASC")
    List<DoctorAvailability> findUpcomingAvailabilityByDoctorId(@Param("doctorId") Long doctorId);
    
    /**
     * Find availability for a specific date range.
     * Used for availability management and reporting.
     */
    @Query("SELECT da FROM DoctorAvailability da WHERE da.doctor.id = :doctorId " +
           "AND da.availableDate BETWEEN :startDate AND :endDate " +
           "ORDER BY da.availableDate ASC, da.startTime ASC")
    List<DoctorAvailability> findByDoctorIdAndDateBetween(
        @Param("doctorId") Long doctorId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    /**
     * Check for overlapping availability slots.
     * Prevents creating conflicting availability slots for the same doctor.
     */
    @Query("SELECT COUNT(da) > 0 FROM DoctorAvailability da WHERE da.doctor.id = :doctorId " +
           "AND da.availableDate = :date " +
           "AND ((da.startTime <= :startTime AND da.endTime > :startTime) " +
           "OR (da.startTime < :endTime AND da.endTime >= :endTime) " +
           "OR (da.startTime >= :startTime AND da.endTime <= :endTime))")
    boolean existsOverlappingSlot(
        @Param("doctorId") Long doctorId,
        @Param("date") LocalDate date,
        @Param("startTime") LocalTime startTime,
        @Param("endTime") LocalTime endTime);
    
    /**
     * Find all available dates for a doctor.
     * Used for calendar view and date selection in appointment booking.
     */
    @Query("SELECT DISTINCT da.availableDate FROM DoctorAvailability da " +
           "WHERE da.doctor.id = :doctorId " +
           "AND da.availableDate >= CURRENT_DATE " +
           "AND da.isAvailable = true " +
           "ORDER BY da.availableDate ASC")
    List<LocalDate> findAvailableDatesByDoctorId(@Param("doctorId") Long doctorId);
    
    /**
     * Find availability by date (all doctors).
     * Used for daily schedule overview and clinic management.
     */
    @Query("SELECT da FROM DoctorAvailability da WHERE da.availableDate = :date " +
           "AND da.isAvailable = true " +
           "ORDER BY da.doctor.name, da.startTime")
    List<DoctorAvailability> findByAvailableDate(@Param("date") LocalDate date);
    
    /**
     * Count available slots for a doctor.
     * Used for doctor workload analysis and reporting.
     */
    @Query("SELECT COUNT(da) FROM DoctorAvailability da WHERE da.doctor.id = :doctorId " +
           "AND da.isAvailable = true " +
           "AND da.availableDate >= CURRENT_DATE")
    Long countAvailableSlotsByDoctorId(@Param("doctorId") Long doctorId);
    
    /**
     * Update availability status.
     * Used when appointments are booked or cancelled.
     */
    @Query("UPDATE DoctorAvailability da SET da.isAvailable = :isAvailable " +
           "WHERE da.doctor.id = :doctorId " +
           "AND da.availableDate = :date " +
           "AND da.startTime = :startTime")
    void updateAvailabilityStatus(
        @Param("doctorId") Long doctorId,
        @Param("date") LocalDate date,
        @Param("startTime") LocalTime startTime,
        @Param("isAvailable") Boolean isAvailable);
}
