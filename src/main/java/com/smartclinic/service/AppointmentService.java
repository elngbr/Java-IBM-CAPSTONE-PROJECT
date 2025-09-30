package com.smartclinic.service;

import com.smartclinic.model.Appointment;
import com.smartclinic.model.Doctor;
import com.smartclinic.model.Patient;
import com.smartclinic.repository.AppointmentRepository;
import com.smartclinic.repository.DoctorRepository;
import com.smartclinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for Appointment entity operations.
 * Contains business logic for appointment management, booking, and scheduling.
 * Required for appointment operations in the Smart Clinic Management System.
 */
@Service
@Transactional
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    /**
     * Create a new appointment.
     * Validates doctor and patient existence, checks for conflicts.
     */
    public Appointment createAppointment(Appointment appointment) {
        // Validate doctor exists
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + appointment.getDoctor().getDoctorId()));

        // Validate patient exists
        Patient patient = patientRepository.findById(appointment.getPatient().getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + appointment.getPatient().getPatientId()));

        // Check for conflicting appointments
        if (appointmentRepository.existsByDoctorIdAndAppointmentTime(
                doctor.getDoctorId(), LocalDateTime.of(appointment.getAppointmentDate(), appointment.getAppointmentTime()))) {
            throw new RuntimeException("Doctor is not available at the requested time");
        }

        // Set entities
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        // Set default status if not provided
        if (appointment.getStatus() == null) {
            appointment.setStatus(Appointment.AppointmentStatus.SCHEDULED);
        }

        return appointmentRepository.save(appointment);
    }

    /**
     * Update an existing appointment.
     * Validates appointment exists and updates allowed fields.
     */
    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        Appointment appointment = getAppointmentById(id);

        // Update allowed fields
        if (appointmentDetails.getAppointmentDate() != null && appointmentDetails.getAppointmentTime() != null) {
            // Check for conflicts if time is being changed
            LocalDateTime newDateTime = LocalDateTime.of(appointmentDetails.getAppointmentDate(), appointmentDetails.getAppointmentTime());
            LocalDateTime currentDateTime = LocalDateTime.of(appointment.getAppointmentDate(), appointment.getAppointmentTime());
            
            if (!currentDateTime.equals(newDateTime)) {
                if (appointmentRepository.existsByDoctorIdAndAppointmentTime(
                        appointment.getDoctor().getDoctorId(), newDateTime)) {
                    throw new RuntimeException("Doctor is not available at the new requested time");
                }
            }
            appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
            appointment.setAppointmentTime(appointmentDetails.getAppointmentTime());
        }

        if (appointmentDetails.getStatus() != null) {
            appointment.setStatus(appointmentDetails.getStatus());
        }

        if (appointmentDetails.getReasonForVisit() != null) {
            appointment.setReasonForVisit(appointmentDetails.getReasonForVisit());
        }

        return appointmentRepository.save(appointment);
    }

    /**
     * Get appointment by ID.
     * Throws exception if not found.
     */
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    /**
     * Get all appointments.
     * Used for admin management and reporting.
     */
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    /**
     * Get appointments by patient ID.
     * Core functionality for patient portal.
     */
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatient_PatientIdOrderByAppointmentTimeDesc(patientId);
    }

    /**
     * Get appointments by doctor ID.
     * Core functionality for doctor portal.
     */
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctor_DoctorIdOrderByAppointmentTimeAsc(doctorId);
    }

    /**
     * Get upcoming appointments for a patient.
     * Used in patient dashboard.
     */
    public List<Appointment> getUpcomingAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findUpcomingAppointmentsByPatientId(patientId);
    }

    /**
     * Get upcoming appointments for a doctor.
     * Used in doctor dashboard.
     */
    public List<Appointment> getUpcomingAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findUpcomingAppointmentsByDoctorId(doctorId);
    }

    /**
     * Get today's appointments for a doctor.
     * Used for daily schedule view.
     */
    public List<Appointment> getTodaysAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findTodaysAppointmentsByDoctorId(doctorId);
    }

    /**
     * Get appointments by status.
     * Used for filtering and management.
     */
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatusOrderByAppointmentTimeAsc(status);
    }

    /**
     * Get appointments for a specific date.
     * Used for daily reports and scheduling.
     */
    public List<Appointment> getAppointmentsByDate(LocalDateTime date) {
        return appointmentRepository.findAppointmentsByDate(date);
    }

    /**
     * Get appointments in a date range.
     * Used for reporting and analytics.
     */
    public List<Appointment> getAppointmentsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findAppointmentsBetweenDates(startDate, endDate);
    }

    /**
     * Cancel an appointment.
     * Updates status to CANCELLED.
     */
    public Appointment cancelAppointment(Long id) {
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus(Appointment.AppointmentStatus.CANCELLED);
        return appointmentRepository.save(appointment);
    }

    /**
     * Complete an appointment.
     * Updates status to COMPLETED.
     */
    public Appointment completeAppointment(Long id) {
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus(Appointment.AppointmentStatus.COMPLETED);
        return appointmentRepository.save(appointment);
    }

    /**
     * Reschedule an appointment.
     * Changes appointment time after checking availability.
     */
    public Appointment rescheduleAppointment(Long id, LocalDateTime newDateTime) {
        Appointment appointment = getAppointmentById(id);

        // Check for conflicts
        if (appointmentRepository.existsByDoctorIdAndAppointmentTime(
                appointment.getDoctor().getDoctorId(), newDateTime)) {
            throw new RuntimeException("Doctor is not available at the new requested time");
        }

        appointment.setAppointmentDate(newDateTime.toLocalDate());
        appointment.setAppointmentTime(newDateTime.toLocalTime());
        appointment.setStatus(Appointment.AppointmentStatus.SCHEDULED);
        return appointmentRepository.save(appointment);
    }

    /**
     * Delete appointment by ID.
     * Admin functionality for appointment management.
     */
    public void deleteAppointment(Long id) {
        Appointment appointment = getAppointmentById(id);
        appointmentRepository.delete(appointment);
    }

    /**
     * Get appointment statistics.
     * Used for dashboard and reporting.
     */
    public Long getTotalAppointmentCount() {
        return appointmentRepository.countTotalAppointments();
    }

    /**
     * Get appointment count by status.
     * Used for status distribution analysis.
     */
    public List<Object[]> getAppointmentCountByStatus() {
        return appointmentRepository.countAppointmentsByStatus();
    }

    /**
     * Get daily appointment report by doctor.
     * Implementation of the stored procedure functionality.
     */
    public List<Appointment> getDailyAppointmentReportByDoctor(LocalDateTime reportDate) {
        return appointmentRepository.findDailyAppointmentReportByDoctor(reportDate);
    }

    /**
     * Check if doctor is available at specific time.
     * Used for appointment validation.
     */
    public boolean isDoctorAvailable(Long doctorId, LocalDateTime dateTime) {
        return !appointmentRepository.existsByDoctorIdAndAppointmentTime(doctorId, dateTime);
    }

    /**
     * Get appointment history between patient and doctor.
     * Used for medical history and continuity of care.
     */
    public List<Appointment> getAppointmentHistory(Long patientId, Long doctorId) {
        return appointmentRepository.findByPatientIdAndDoctorId(patientId, doctorId);
    }
}
