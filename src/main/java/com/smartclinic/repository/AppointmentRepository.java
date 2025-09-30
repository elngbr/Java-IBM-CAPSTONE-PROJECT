package com.smartclinic.repository;

import com.smartclinic.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Appointment entity operations.
 * Provides CRUD operations and custom queries for appointment management.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    /**
     * Find appointments by patient ID.
     * Core functionality for patient portal - viewing their appointments.
     */
    List<Appointment> findByPatient_PatientIdOrderByAppointmentTimeDesc(Long patientId);
    
    /**
     * Find appointments by doctor ID.
     * Core functionality for doctor portal - viewing their appointments.
     */
    List<Appointment> findByDoctor_DoctorIdOrderByAppointmentTimeAsc(Long doctorId);
    
    /**
     * Find appointments by status.
     * Used for filtering appointments by their current status.
     */
    List<Appointment> findByStatusOrderByAppointmentTimeAsc(String status);
    
    /**
     * Find upcoming appointments for a patient.
     * Used in patient dashboard to show next appointments.
     */
    @Query("SELECT a FROM Appointment a WHERE a.patient.patientId = :patientId " +
           "AND a.appointmentTime >= CURRENT_TIMESTAMP " +
           "ORDER BY a.appointmentTime ASC")
    List<Appointment> findUpcomingAppointmentsByPatientId(@Param("patientId") Long patientId);
    
    /**
     * Find upcoming appointments for a doctor.
     * Used in doctor dashboard to show today's and upcoming appointments.
     */
    @Query("SELECT a FROM Appointment a WHERE a.doctor.doctorId = :doctorId " +
           "AND a.appointmentTime >= CURRENT_TIMESTAMP " +
           "ORDER BY a.appointmentTime ASC")
    List<Appointment> findUpcomingAppointmentsByDoctorId(@Param("doctorId") Long doctorId);
    
    /**
     * Find appointments for a specific date.
     * Used for daily appointment reports and scheduling.
     */
    @Query("SELECT a FROM Appointment a WHERE DATE(a.appointmentTime) = DATE(:date) " +
           "ORDER BY a.appointmentTime ASC")
    List<Appointment> findAppointmentsByDate(@Param("date") LocalDateTime date);
    
    /**
     * Find appointments in a date range.
     * Used for reporting and analytics.
     */
    @Query("SELECT a FROM Appointment a WHERE a.appointmentTime BETWEEN :startDate AND :endDate " +
           "ORDER BY a.appointmentTime ASC")
    List<Appointment> findAppointmentsBetweenDates(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate);
    
    /**
     * Check for conflicting appointments.
     * Prevents double-booking of doctors at the same time.
     */
    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.doctor.doctorId = :doctorId " +
           "AND a.appointmentTime = :appointmentTime " +
           "AND a.status != 'CANCELLED'")
    boolean existsByDoctorIdAndAppointmentTime(
        @Param("doctorId") Long doctorId,
        @Param("appointmentTime") LocalDateTime appointmentTime);
    
    /**
     * Find appointments by patient and doctor.
     * Used for patient history with specific doctors.
     */
    @Query("SELECT a FROM Appointment a WHERE a.patient.patientId = :patientId " +
           "AND a.doctor.doctorId = :doctorId " +
           "ORDER BY a.appointmentTime DESC")
    List<Appointment> findByPatientIdAndDoctorId(
        @Param("patientId") Long patientId,
        @Param("doctorId") Long doctorId);
    
    /**
     * Count appointments by status.
     * Used for dashboard statistics and reporting.
     */
    @Query("SELECT a.status, COUNT(a) FROM Appointment a GROUP BY a.status")
    List<Object[]> countAppointmentsByStatus();
    
    /**
     * Find today's appointments for a doctor.
     * Used for doctor's daily schedule view.
     */
    @Query("SELECT a FROM Appointment a WHERE a.doctor.doctorId = :doctorId " +
           "AND DATE(a.appointmentTime) = CURRENT_DATE " +
           "ORDER BY a.appointmentTime ASC")
    List<Appointment> findTodaysAppointmentsByDoctorId(@Param("doctorId") Long doctorId);
    
    /**
     * Count total appointments.
     * Used for dashboard statistics.
     */
    @Query("SELECT COUNT(a) FROM Appointment a")
    Long countTotalAppointments();
    
    /**
     * Find appointments for daily report by doctor.
     * Used by the GetDailyAppointmentReportByDoctor stored procedure equivalent.
     */
    @Query("SELECT a FROM Appointment a " +
           "JOIN FETCH a.doctor d " +
           "JOIN FETCH a.patient p " +
           "WHERE DATE(a.appointmentTime) = DATE(:reportDate) " +
           "ORDER BY d.firstName, d.lastName, a.appointmentTime")
    List<Appointment> findDailyAppointmentReportByDoctor(@Param("reportDate") LocalDateTime reportDate);
}
