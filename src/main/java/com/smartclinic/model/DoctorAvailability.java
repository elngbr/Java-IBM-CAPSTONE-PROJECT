package com.smartclinic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * DoctorAvailability entity representing doctor schedule exceptions
 * Manages doctor unavailability and special availability slots
 */
@Entity
@Table(name = "doctor_availability")
public class DoctorAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id")
    @JsonProperty("availabilityId")
    private Long availabilityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor cannot be null")
    @JsonBackReference("doctor-availability")
    private Doctor doctor;

    @Column(name = "date", nullable = false)
    @NotNull(message = "Date cannot be null")
    @JsonProperty("date")
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    @NotNull(message = "Start time cannot be null")
    @JsonProperty("startTime")
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    @NotNull(message = "End time cannot be null")
    @JsonProperty("endTime")
    private LocalTime endTime;

    @Column(name = "availability_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Availability type cannot be null")
    @JsonProperty("availabilityType")
    private AvailabilityType availabilityType = AvailabilityType.UNAVAILABLE;

    @Column(name = "reason", length = 200)
    @Size(max = 200, message = "Reason must not exceed 200 characters")
    @JsonProperty("reason")
    private String reason;

    @Column(name = "is_recurring", nullable = false)
    @JsonProperty("isRecurring")
    private Boolean isRecurring = false;

    @Column(name = "recurrence_pattern", length = 50)
    @Size(max = 50, message = "Recurrence pattern must not exceed 50 characters")
    @JsonProperty("recurrencePattern")
    private String recurrencePattern;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    // Enum for Availability Type
    public enum AvailabilityType {
        AVAILABLE("Available"),
        UNAVAILABLE("Unavailable"),
        BREAK("Break");

        private final String displayName;

        AvailabilityType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Default constructor
    public DoctorAvailability() {
    }

    // Constructor with required fields
    public DoctorAvailability(Doctor doctor, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.doctor = doctor;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public Long getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Long availabilityId) {
        this.availabilityId = availabilityId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public AvailabilityType getAvailabilityType() {
        return availabilityType;
    }

    public void setAvailabilityType(AvailabilityType availabilityType) {
        this.availabilityType = availabilityType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getIsRecurring() {
        return isRecurring;
    }

    public void setIsRecurring(Boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public String getRecurrencePattern() {
        return recurrencePattern;
    }

    public void setRecurrencePattern(String recurrencePattern) {
        this.recurrencePattern = recurrencePattern;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Helper methods
    @JsonProperty("doctorId")
    public Long getDoctorId() {
        return doctor != null ? doctor.getDoctorId() : null;
    }

    // equals, hashCode, and toString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorAvailability that = (DoctorAvailability) o;
        return Objects.equals(availabilityId, that.availabilityId) &&
                Objects.equals(doctor, that.doctor) &&
                Objects.equals(date, that.date) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(availabilityId, doctor, date, startTime, endTime);
    }

    @Override
    public String toString() {
        return "DoctorAvailability{" +
                "availabilityId=" + availabilityId +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", availabilityType=" + availabilityType +
                ", reason='" + reason + '\'' +
                ", isRecurring=" + isRecurring +
                '}';
    }
}
