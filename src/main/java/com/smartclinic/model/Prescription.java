package com.smartclinic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Prescription document for MongoDB storage
 * Manages flexible prescription data with nested medication information
 */
@Document(collection = "prescriptions")
public class Prescription {

    @Id
    @JsonProperty("id")
    private String id;

    @Field("appointmentId")
    @NotNull(message = "Appointment ID cannot be null")
    @JsonProperty("appointmentId")
    private Long appointmentId;

    @Field("patientId")
    @NotNull(message = "Patient ID cannot be null")
    @JsonProperty("patientId")
    private Long patientId;

    @Field("doctorId")
    @NotNull(message = "Doctor ID cannot be null")
    @JsonProperty("doctorId")
    private Long doctorId;

    @Field("prescriptionDate")
    @NotNull(message = "Prescription date cannot be null")
    @JsonProperty("prescriptionDate")
    private LocalDateTime prescriptionDate;

    @Field("patientInfo")
    @NotNull(message = "Patient info cannot be null")
    @JsonProperty("patientInfo")
    private PatientInfo patientInfo;

    @Field("doctorInfo")
    @NotNull(message = "Doctor info cannot be null")
    @JsonProperty("doctorInfo")
    private DoctorInfo doctorInfo;

    @Field("diagnosis")
    @JsonProperty("diagnosis")
    private Diagnosis diagnosis;

    @Field("medications")
    @NotEmpty(message = "At least one medication must be prescribed")
    @JsonProperty("medications")
    private List<Medication> medications = new ArrayList<>();

    @Field("additionalInstructions")
    @JsonProperty("additionalInstructions")
    private List<String> additionalInstructions = new ArrayList<>();

    @Field("pharmacyInfo")
    @JsonProperty("pharmacyInfo")
    private PharmacyInfo pharmacyInfo;

    @Field("digitalSignature")
    @JsonProperty("digitalSignature")
    private DigitalSignature digitalSignature;

    @Field("status")
    @NotNull(message = "Status cannot be null")
    @JsonProperty("status")
    private PrescriptionStatus status = PrescriptionStatus.ACTIVE;

    @CreatedDate
    @Field("createdAt")
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updatedAt")
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    // Nested classes for embedded documents
    public static class PatientInfo {
        @JsonProperty("name")
        private String name;

        @JsonProperty("age")
        private Integer age;

        @JsonProperty("weight")
        private Double weight;

        @JsonProperty("allergies")
        private List<String> allergies = new ArrayList<>();

        // Constructors, getters, and setters
        public PatientInfo() {}

        public PatientInfo(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
        public Double getWeight() { return weight; }
        public void setWeight(Double weight) { this.weight = weight; }
        public List<String> getAllergies() { return allergies; }
        public void setAllergies(List<String> allergies) { this.allergies = allergies; }
    }

    public static class DoctorInfo {
        @JsonProperty("name")
        private String name;

        @JsonProperty("licenseNumber")
        private String licenseNumber;

        @JsonProperty("specialization")
        private String specialization;

        // Constructors, getters, and setters
        public DoctorInfo() {}

        public DoctorInfo(String name, String licenseNumber, String specialization) {
            this.name = name;
            this.licenseNumber = licenseNumber;
            this.specialization = specialization;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getLicenseNumber() { return licenseNumber; }
        public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
        public String getSpecialization() { return specialization; }
        public void setSpecialization(String specialization) { this.specialization = specialization; }
    }

    public static class Diagnosis {
        @JsonProperty("primary")
        private String primary;

        @JsonProperty("secondary")
        private List<String> secondary = new ArrayList<>();

        @JsonProperty("icdCodes")
        private List<String> icdCodes = new ArrayList<>();

        // Constructors, getters, and setters
        public Diagnosis() {}

        public Diagnosis(String primary) {
            this.primary = primary;
        }

        public String getPrimary() { return primary; }
        public void setPrimary(String primary) { this.primary = primary; }
        public List<String> getSecondary() { return secondary; }
        public void setSecondary(List<String> secondary) { this.secondary = secondary; }
        public List<String> getIcdCodes() { return icdCodes; }
        public void setIcdCodes(List<String> icdCodes) { this.icdCodes = icdCodes; }
    }

    public static class Medication {
        @NotBlank(message = "Medication name cannot be blank")
        @JsonProperty("medicationName")
        private String medicationName;

        @JsonProperty("genericName")
        private String genericName;

        @NotBlank(message = "Dosage cannot be blank")
        @JsonProperty("dosage")
        private String dosage;

        @NotBlank(message = "Frequency cannot be blank")
        @JsonProperty("frequency")
        private String frequency;

        @NotBlank(message = "Duration cannot be blank")
        @JsonProperty("duration")
        private String duration;

        @Min(value = 1, message = "Quantity must be at least 1")
        @JsonProperty("quantity")
        private Integer quantity;

        @Min(value = 0, message = "Refills cannot be negative")
        @JsonProperty("refills")
        private Integer refills = 0;

        @JsonProperty("instructions")
        private String instructions;

        @JsonProperty("sideEffects")
        private List<String> sideEffects = new ArrayList<>();

        @JsonProperty("interactions")
        private List<String> interactions = new ArrayList<>();

        // Constructors, getters, and setters
        public Medication() {}

        public Medication(String medicationName, String dosage, String frequency, String duration, Integer quantity) {
            this.medicationName = medicationName;
            this.dosage = dosage;
            this.frequency = frequency;
            this.duration = duration;
            this.quantity = quantity;
        }

        // Getters and setters
        public String getMedicationName() { return medicationName; }
        public void setMedicationName(String medicationName) { this.medicationName = medicationName; }
        public String getGenericName() { return genericName; }
        public void setGenericName(String genericName) { this.genericName = genericName; }
        public String getDosage() { return dosage; }
        public void setDosage(String dosage) { this.dosage = dosage; }
        public String getFrequency() { return frequency; }
        public void setFrequency(String frequency) { this.frequency = frequency; }
        public String getDuration() { return duration; }
        public void setDuration(String duration) { this.duration = duration; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public Integer getRefills() { return refills; }
        public void setRefills(Integer refills) { this.refills = refills; }
        public String getInstructions() { return instructions; }
        public void setInstructions(String instructions) { this.instructions = instructions; }
        public List<String> getSideEffects() { return sideEffects; }
        public void setSideEffects(List<String> sideEffects) { this.sideEffects = sideEffects; }
        public List<String> getInteractions() { return interactions; }
        public void setInteractions(List<String> interactions) { this.interactions = interactions; }
    }

    public static class PharmacyInfo {
        @JsonProperty("name")
        private String name;

        @JsonProperty("address")
        private String address;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("faxSent")
        private Boolean faxSent = false;

        @JsonProperty("sentAt")
        private LocalDateTime sentAt;

        // Constructors, getters, and setters
        public PharmacyInfo() {}

        public PharmacyInfo(String name, String address, String phone) {
            this.name = name;
            this.address = address;
            this.phone = phone;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public Boolean getFaxSent() { return faxSent; }
        public void setFaxSent(Boolean faxSent) { this.faxSent = faxSent; }
        public LocalDateTime getSentAt() { return sentAt; }
        public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
    }

    public static class DigitalSignature {
        @JsonProperty("signed")
        private Boolean signed = false;

        @JsonProperty("signedAt")
        private LocalDateTime signedAt;

        @JsonProperty("signatureHash")
        private String signatureHash;

        // Constructors, getters, and setters
        public DigitalSignature() {}

        public Boolean getSigned() { return signed; }
        public void setSigned(Boolean signed) { this.signed = signed; }
        public LocalDateTime getSignedAt() { return signedAt; }
        public void setSignedAt(LocalDateTime signedAt) { this.signedAt = signedAt; }
        public String getSignatureHash() { return signatureHash; }
        public void setSignatureHash(String signatureHash) { this.signatureHash = signatureHash; }
    }

    // Enum for Prescription Status
    public enum PrescriptionStatus {
        ACTIVE("Active"),
        FILLED("Filled"),
        EXPIRED("Expired"),
        CANCELLED("Cancelled");

        private final String displayName;

        PrescriptionStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Default constructor
    public Prescription() {
        this.prescriptionDate = LocalDateTime.now();
    }

    // Constructor with required fields
    public Prescription(Long appointmentId, Long patientId, Long doctorId, 
                       PatientInfo patientInfo, DoctorInfo doctorInfo) {
        this();
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.patientInfo = patientInfo;
        this.doctorInfo = doctorInfo;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDateTime prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public PatientInfo getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    public DoctorInfo getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(DoctorInfo doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public List<String> getAdditionalInstructions() {
        return additionalInstructions;
    }

    public void setAdditionalInstructions(List<String> additionalInstructions) {
        this.additionalInstructions = additionalInstructions;
    }

    public PharmacyInfo getPharmacyInfo() {
        return pharmacyInfo;
    }

    public void setPharmacyInfo(PharmacyInfo pharmacyInfo) {
        this.pharmacyInfo = pharmacyInfo;
    }

    public DigitalSignature getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(DigitalSignature digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
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
    public void addMedication(Medication medication) {
        if (medications == null) {
            medications = new ArrayList<>();
        }
        medications.add(medication);
    }

    public void addAdditionalInstruction(String instruction) {
        if (additionalInstructions == null) {
            additionalInstructions = new ArrayList<>();
        }
        additionalInstructions.add(instruction);
    }

    public boolean isSigned() {
        return digitalSignature != null && Boolean.TRUE.equals(digitalSignature.getSigned());
    }

    public boolean isActive() {
        return PrescriptionStatus.ACTIVE.equals(status);
    }

    public int getMedicationCount() {
        return medications != null ? medications.size() : 0;
    }

    public String getPatientName() {
        return patientInfo != null ? patientInfo.getName() : "Unknown Patient";
    }

    public String getDoctorName() {
        return doctorInfo != null ? doctorInfo.getName() : "Unknown Doctor";
    }

    // equals, hashCode, and toString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(appointmentId, that.appointmentId) &&
                Objects.equals(patientId, that.patientId) &&
                Objects.equals(doctorId, that.doctorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appointmentId, patientId, doctorId);
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "id='" + id + '\'' +
                ", appointmentId=" + appointmentId +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", prescriptionDate=" + prescriptionDate +
                ", medicationCount=" + getMedicationCount() +
                ", status=" + status +
                ", isSigned=" + isSigned() +
                '}';
    }
}
