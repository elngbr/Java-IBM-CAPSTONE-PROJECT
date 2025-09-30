package com.smartclinic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Doctor entity representing medical professionals in the clinic
 * Manages doctor profiles, credentials, and availability
 */
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    @JsonProperty("doctorId")
    private Long doctorId;

    @Column(name = "email", unique = true, nullable = false)
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    @JsonProperty("email")
    private String email;

    @Column(name = "password_hash", nullable = false)
    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordHash;

    @Column(name = "first_name", nullable = false, length = 100)
    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "First name can only contain letters and spaces")
    @JsonProperty("firstName")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Last name can only contain letters and spaces")
    @JsonProperty("lastName")
    private String lastName;

    @Column(name = "specialization", nullable = false, length = 200)
    @NotNull(message = "Specialization cannot be null")
    @NotBlank(message = "Specialization cannot be blank")
    @Size(max = 200, message = "Specialization must not exceed 200 characters")
    @JsonProperty("specialization")
    private String specialization;

    @Column(name = "license_number", unique = true, nullable = false, length = 50)
    @NotNull(message = "License number cannot be null")
    @NotBlank(message = "License number cannot be blank")
    @Size(max = 50, message = "License number must not exceed 50 characters")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "License number must contain only uppercase letters and numbers")
    @JsonProperty("licenseNumber")
    private String licenseNumber;

    @Column(name = "phone_number", nullable = false, length = 20)
    @NotNull(message = "Phone number cannot be null")
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be a valid format")
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @Column(name = "bio", columnDefinition = "TEXT")
    @Size(max = 1000, message = "Bio must not exceed 1000 characters")
    @JsonProperty("bio")
    private String bio;

    @Column(name = "years_experience")
    @Min(value = 0, message = "Years of experience cannot be negative")
    @Max(value = 50, message = "Years of experience cannot exceed 50")
    @JsonProperty("yearsExperience")
    private Integer yearsExperience = 0;

    @Column(name = "consultation_fee", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Consultation fee must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Consultation fee must be a valid currency amount")
    @JsonProperty("consultationFee")
    private BigDecimal consultationFee;

    @Column(name = "office_location", length = 200)
    @Size(max = 200, message = "Office location must not exceed 200 characters")
    @JsonProperty("officeLocation")
    private String officeLocation;

    @Column(name = "working_hours_start")
    @JsonProperty("workingHoursStart")
    private LocalTime workingHoursStart = LocalTime.of(9, 0);

    @Column(name = "working_hours_end")
    @JsonProperty("workingHoursEnd")
    private LocalTime workingHoursEnd = LocalTime.of(17, 0);

    @Column(name = "profile_image_url", length = 500)
    @Size(max = 500, message = "Profile image URL must not exceed 500 characters")
    @JsonProperty("profileImageUrl")
    private String profileImageUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    @JsonProperty("isActive")
    private Boolean isActive = true;

    // Relationships
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("doctor-appointments")
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("doctor-availability")
    private List<DoctorAvailability> availabilitySlots = new ArrayList<>();

    // Default constructor
    public Doctor() {
    }

    // Constructor with required fields
    public Doctor(String email, String passwordHash, String firstName, String lastName, 
                  String specialization, String licenseNumber, String phoneNumber) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.licenseNumber = licenseNumber;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(Integer yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(BigDecimal consultationFee) {
        this.consultationFee = consultationFee;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public LocalTime getWorkingHoursStart() {
        return workingHoursStart;
    }

    public void setWorkingHoursStart(LocalTime workingHoursStart) {
        this.workingHoursStart = workingHoursStart;
    }

    public LocalTime getWorkingHoursEnd() {
        return workingHoursEnd;
    }

    public void setWorkingHoursEnd(LocalTime workingHoursEnd) {
        this.workingHoursEnd = workingHoursEnd;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<DoctorAvailability> getAvailabilitySlots() {
        return availabilitySlots;
    }

    public void setAvailabilitySlots(List<DoctorAvailability> availabilitySlots) {
        this.availabilitySlots = availabilitySlots;
    }

    // Helper methods
    public String getFullName() {
        return "Dr. " + firstName + " " + lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public boolean isActive() {
        return Boolean.TRUE.equals(isActive);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.setDoctor(this);
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
        appointment.setDoctor(null);
    }

    public void addAvailabilitySlot(DoctorAvailability slot) {
        availabilitySlots.add(slot);
        slot.setDoctor(this);
    }

    public void removeAvailabilitySlot(DoctorAvailability slot) {
        availabilitySlots.remove(slot);
        slot.setDoctor(null);
    }

    /**
     * Check if doctor is available during working hours
     */
    public boolean isWorkingTime(LocalTime time) {
        return time.isAfter(workingHoursStart.minusMinutes(1)) && 
               time.isBefore(workingHoursEnd.plusMinutes(1));
    }

    /**
     * Get available times as formatted string array
     */
    public List<String> getAvailableTimes() {
        List<String> times = new ArrayList<>();
        LocalTime current = workingHoursStart;
        
        while (current.isBefore(workingHoursEnd)) {
            times.add(current.toString());
            current = current.plusMinutes(60); // 1-hour slots
        }
        
        return times;
    }

    // equals, hashCode, and toString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(doctorId, doctor.doctorId) &&
                Objects.equals(email, doctor.email) &&
                Objects.equals(licenseNumber, doctor.licenseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, email, licenseNumber);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId=" + doctorId +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialization='" + specialization + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", yearsExperience=" + yearsExperience +
                ", isActive=" + isActive +
                '}';
    }
}
