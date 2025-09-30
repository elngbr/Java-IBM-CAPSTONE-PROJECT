package com.smartclinic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Patient entity representing clinic patients
 * Manages patient demographics, medical information, and appointments
 */
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    @JsonProperty("patientId")
    private Long patientId;

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

    @Column(name = "phone_number", nullable = false, length = 20)
    @NotNull(message = "Phone number cannot be null")
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be a valid format")
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @Column(name = "date_of_birth", nullable = false)
    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    @JsonProperty("dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender cannot be null")
    @JsonProperty("gender")
    private Gender gender;

    @Column(name = "address", columnDefinition = "TEXT")
    @Size(max = 500, message = "Address must not exceed 500 characters")
    @JsonProperty("address")
    private String address;

    @Column(name = "emergency_contact_name", length = 200)
    @Size(max = 200, message = "Emergency contact name must not exceed 200 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Emergency contact name can only contain letters and spaces")
    @JsonProperty("emergencyContactName")
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone", length = 20)
    @Pattern(regexp = "^(\\+?[1-9]\\d{1,14})?$", message = "Emergency contact phone must be a valid format")
    @JsonProperty("emergencyContactPhone")
    private String emergencyContactPhone;

    @Column(name = "blood_type", length = 5)
    @Pattern(regexp = "^(A|B|AB|O)[+-]?$", message = "Blood type must be valid (A, B, AB, O with optional + or -)")
    @JsonProperty("bloodType")
    private String bloodType;

    @Column(name = "allergies", columnDefinition = "TEXT")
    @Size(max = 1000, message = "Allergies information must not exceed 1000 characters")
    @JsonProperty("allergies")
    private String allergies;

    @Column(name = "medical_history", columnDefinition = "TEXT")
    @Size(max = 2000, message = "Medical history must not exceed 2000 characters")
    @JsonProperty("medicalHistory")
    private String medicalHistory;

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
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("patient-appointments")
    private List<Appointment> appointments = new ArrayList<>();

    // Enum for Gender
    public enum Gender {
        MALE("Male"),
        FEMALE("Female"),
        OTHER("Other");

        private final String displayName;

        Gender(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Default constructor
    public Patient() {
    }

    // Constructor with required fields
    public Patient(String email, String passwordHash, String firstName, String lastName, 
                   String phoneNumber, LocalDate dateOfBirth, Gender gender) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    // Getters and Setters
    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
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

    // Helper methods
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isActive() {
        return Boolean.TRUE.equals(isActive);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.setPatient(this);
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
        appointment.setPatient(null);
    }

    /**
     * Calculate patient's age based on date of birth
     */
    public int getAge() {
        if (dateOfBirth == null) {
            return 0;
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    /**
     * Check if patient is a minor (under 18)
     */
    public boolean isMinor() {
        return getAge() < 18;
    }

    /**
     * Check if patient has any known allergies
     */
    public boolean hasAllergies() {
        return allergies != null && !allergies.trim().isEmpty();
    }

    /**
     * Check if patient has medical history
     */
    public boolean hasMedicalHistory() {
        return medicalHistory != null && !medicalHistory.trim().isEmpty();
    }

    /**
     * Get emergency contact information as formatted string
     */
    public String getEmergencyContactInfo() {
        if (emergencyContactName == null || emergencyContactName.trim().isEmpty()) {
            return "No emergency contact provided";
        }
        
        StringBuilder contact = new StringBuilder(emergencyContactName);
        if (emergencyContactPhone != null && !emergencyContactPhone.trim().isEmpty()) {
            contact.append(" - ").append(emergencyContactPhone);
        }
        return contact.toString();
    }

    // equals, hashCode, and toString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(patientId, patient.patientId) &&
                Objects.equals(email, patient.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, email);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", isActive=" + isActive +
                ", age=" + getAge() +
                '}';
    }
}
