package com.smartclinic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Appointment entity representing scheduled medical appointments
 * Links patients and doctors with appointment details
 */
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    @JsonProperty("appointmentId")
    private Long appointmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull(message = "Patient cannot be null")
    @JsonBackReference("patient-appointments")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor cannot be null")
    @JsonBackReference("doctor-appointments")
    private Doctor doctor;

    @Column(name = "appointment_date", nullable = false)
    @NotNull(message = "Appointment date cannot be null")
    @Future(message = "Appointment date must be in the future")
    @JsonProperty("appointmentDate")
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    @NotNull(message = "Appointment time cannot be null")
    @JsonProperty("appointmentTime")
    private LocalTime appointmentTime;

    @Column(name = "duration_minutes", nullable = false)
    @Min(value = 15, message = "Appointment duration must be at least 15 minutes")
    @Max(value = 480, message = "Appointment duration cannot exceed 8 hours")
    @JsonProperty("durationMinutes")
    private Integer durationMinutes = 60;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Appointment status cannot be null")
    @JsonProperty("status")
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    @Column(name = "appointment_type", length = 100)
    @Size(max = 100, message = "Appointment type must not exceed 100 characters")
    @JsonProperty("appointmentType")
    private String appointmentType = "Consultation";

    @Column(name = "reason_for_visit", columnDefinition = "TEXT")
    @Size(max = 1000, message = "Reason for visit must not exceed 1000 characters")
    @JsonProperty("reasonForVisit")
    private String reasonForVisit;

    @Column(name = "notes", columnDefinition = "TEXT")
    @Size(max = 2000, message = "Notes must not exceed 2000 characters")
    @JsonProperty("notes")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    // Enum for Appointment Status
    public enum AppointmentStatus {
        SCHEDULED("Scheduled"),
        CONFIRMED("Confirmed"),
        IN_PROGRESS("In-Progress"),
        COMPLETED("Completed"),
        CANCELLED("Cancelled"),
        NO_SHOW("No-Show");

        private final String displayName;

        AppointmentStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Default constructor
    public Appointment() {
    }

    // Constructor with required fields
    public Appointment(Patient patient, Doctor doctor, LocalDate appointmentDate, LocalTime appointmentTime) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    // Constructor with all main fields
    public Appointment(Patient patient, Doctor doctor, LocalDate appointmentDate, 
                      LocalTime appointmentTime, Integer durationMinutes, String reasonForVisit) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.durationMinutes = durationMinutes;
        this.reasonForVisit = reasonForVisit;
    }

    // Getters and Setters
    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getReasonForVisit() {
        return reasonForVisit;
    }

    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Helper methods
    /**
     * Calculate the end time of the appointment
     * @return LocalTime representing when the appointment ends
     */
    public LocalTime getEndTime() {
        if (appointmentTime == null || durationMinutes == null) {
            return null;
        }
        return appointmentTime.plusMinutes(durationMinutes);
    }

    /**
     * Get the complete appointment date and time as LocalDateTime
     * @return LocalDateTime combining appointment date and time
     */
    public LocalDateTime getAppointmentDateTime() {
        if (appointmentDate == null || appointmentTime == null) {
            return null;
        }
        return LocalDateTime.of(appointmentDate, appointmentTime);
    }

    /**
     * Get the complete appointment end date and time as LocalDateTime
     * @return LocalDateTime representing when the appointment ends
     */
    public LocalDateTime getAppointmentEndDateTime() {
        LocalTime endTime = getEndTime();
        if (appointmentDate == null || endTime == null) {
            return null;
        }
        return LocalDateTime.of(appointmentDate, endTime);
    }

    /**
     * Check if the appointment is today
     * @return true if appointment is scheduled for today
     */
    public boolean isToday() {
        if (appointmentDate == null) {
            return false;
        }
        return appointmentDate.equals(LocalDate.now());
    }

    /**
     * Check if the appointment is in the past
     * @return true if appointment date/time has passed
     */
    public boolean isPast() {
        LocalDateTime appointmentDateTime = getAppointmentDateTime();
        if (appointmentDateTime == null) {
            return false;
        }
        return appointmentDateTime.isBefore(LocalDateTime.now());
    }

    /**
     * Check if the appointment is upcoming (in the future)
     * @return true if appointment is scheduled for the future
     */
    public boolean isUpcoming() {
        LocalDateTime appointmentDateTime = getAppointmentDateTime();
        if (appointmentDateTime == null) {
            return false;
        }
        return appointmentDateTime.isAfter(LocalDateTime.now());
    }

    /**
     * Check if the appointment can be cancelled
     * @return true if appointment can be cancelled
     */
    public boolean canBeCancelled() {
        return status == AppointmentStatus.SCHEDULED || 
               status == AppointmentStatus.CONFIRMED;
    }

    /**
     * Check if the appointment is active (not cancelled or completed)
     * @return true if appointment is active
     */
    public boolean isActive() {
        return status != AppointmentStatus.CANCELLED && 
               status != AppointmentStatus.COMPLETED && 
               status != AppointmentStatus.NO_SHOW;
    }

    /**
     * Get formatted appointment time range (e.g., "2:30 PM - 3:30 PM")
     * @return formatted time range string
     */
    public String getTimeRange() {
        if (appointmentTime == null) {
            return "Time not set";
        }
        
        LocalTime endTime = getEndTime();
        if (endTime == null) {
            return appointmentTime.toString();
        }
        
        return appointmentTime.toString() + " - " + endTime.toString();
    }

    /**
     * Get patient's full name (convenience method)
     * @return patient's full name or "Unknown Patient"
     */
    public String getPatientName() {
        return patient != null ? patient.getFullName() : "Unknown Patient";
    }

    /**
     * Get doctor's full name (convenience method)
     * @return doctor's full name or "Unknown Doctor"
     */
    public String getDoctorName() {
        return doctor != null ? doctor.getFullName() : "Unknown Doctor";
    }

    /**
     * Get patient ID (convenience method for JSON serialization)
     * @return patient ID or null
     */
    @JsonProperty("patientId")
    public Long getPatientId() {
        return patient != null ? patient.getPatientId() : null;
    }

    /**
     * Get doctor ID (convenience method for JSON serialization)
     * @return doctor ID or null
     */
    @JsonProperty("doctorId")
    public Long getDoctorId() {
        return doctor != null ? doctor.getDoctorId() : null;
    }

    // equals, hashCode, and toString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(appointmentId, that.appointmentId) &&
                Objects.equals(appointmentDate, that.appointmentDate) &&
                Objects.equals(appointmentTime, that.appointmentTime) &&
                Objects.equals(patient, that.patient) &&
                Objects.equals(doctor, that.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentId, appointmentDate, appointmentTime, patient, doctor);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", durationMinutes=" + durationMinutes +
                ", status=" + status +
                ", appointmentType='" + appointmentType + '\'' +
                ", patientName='" + getPatientName() + '\'' +
                ", doctorName='" + getDoctorName() + '\'' +
                '}';
    }
}
