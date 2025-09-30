# Smart Clinic Management System - Models Documentation

## Overview

This project contains the Java model classes for the Smart Clinic Management System, implementing a hybrid database architecture using both MySQL (JPA) and MongoDB for optimal data storage and retrieval.

## Model Architecture

### JPA Entities (MySQL)

#### 1. Admin (`Admin.java`)
- **Purpose**: System administrator accounts and permissions
- **Key Features**:
  - Role-based access control (Super Admin, Admin, Support)
  - JSON permissions field for flexible authorization
  - Secure password handling with write-only access
  - Audit trail with creation and update timestamps

#### 2. Doctor (`Doctor.java`)
- **Purpose**: Medical professional profiles and credentials
- **Key Features**:
  - Medical license validation with unique constraints
  - Specialization and experience tracking
  - Working hours and availability management
  - Consultation fee handling with decimal precision
  - Relationship management with appointments and availability slots

#### 3. Patient (`Patient.java`)
- **Purpose**: Patient demographics and medical information
- **Key Features**:
  - Comprehensive demographic data with validation
  - Medical history and allergy tracking
  - Emergency contact information
  - Age calculation and minor status checking
  - GDPR-compliant data handling

#### 4. Appointment (`Appointment.java`)
- **Purpose**: Medical appointment scheduling and management
- **Key Features**:
  - Doctor-Patient relationship linking
  - Flexible appointment duration (default 60 minutes)
  - Status tracking throughout appointment lifecycle
  - Helper methods for time calculations (`getEndTime()`)
  - Conflict prevention with unique constraints

#### 5. DoctorAvailability (`DoctorAvailability.java`)
- **Purpose**: Doctor schedule exceptions and availability management
- **Key Features**:
  - Available/Unavailable/Break time slot management
  - Recurring pattern support
  - Reason tracking for transparency

### MongoDB Documents

#### 1. Prescription (`Prescription.java`)
- **Purpose**: Flexible prescription management with nested medication data
- **Key Features**:
  - Embedded patient and doctor information for quick access
  - Complex medication arrays with dosage, interactions, and side effects
  - Digital signature support for legal compliance
  - Pharmacy integration fields
  - Flexible schema for evolving prescription requirements

## Model Features

### Validation Annotations
- **Bean Validation**: Comprehensive field validation using Jakarta Bean Validation
- **Custom Patterns**: Email, phone number, and medical license validation
- **Size Constraints**: Appropriate field length limits
- **Business Rules**: Age validation, appointment time constraints

### JSON Serialization
- **Jackson Annotations**: Proper JSON property mapping
- **Security**: Password fields marked as write-only
- **Null Handling**: Non-null inclusion for cleaner API responses
- **Date Formatting**: ISO format for consistent date/time handling

### Relationship Management
- **JPA Relationships**: Proper foreign key relationships with cascade options
- **Bidirectional Mapping**: JSON managed/back references to prevent circular serialization
- **Lazy Loading**: Performance optimization with fetch strategies
- **Helper Methods**: Convenient relationship management methods

### Audit Trail
- **Timestamps**: Automatic creation and update tracking
- **Soft Deletes**: `isActive` flags for data retention
- **Change Tracking**: Comprehensive audit capabilities

## Database Schema Alignment

These models align with the database schema designed in `schema-design.md`:

### MySQL Tables
- `admins` → Admin entity
- `doctors` → Doctor entity  
- `patients` → Patient entity
- `appointments` → Appointment entity
- `doctor_availability` → DoctorAvailability entity

### MongoDB Collections
- `prescriptions` → Prescription document

## Usage Examples

### Creating a Doctor
```java
Doctor doctor = new Doctor(
    "doctor@clinic.com",
    passwordEncoder.encode("securePassword"),
    "Sarah",
    "Johnson",
    "Internal Medicine",
    "MD123456",
    "+1-555-123-4567"
);
doctor.setYearsExperience(10);
doctor.setConsultationFee(new BigDecimal("150.00"));
```

### Creating an Appointment
```java
Appointment appointment = new Appointment(
    patient,
    doctor,
    LocalDate.of(2025, 10, 15),
    LocalTime.of(14, 30)
);
appointment.setReasonForVisit("Annual checkup");
appointment.setDurationMinutes(60);
```

### Creating a Prescription
```java
Prescription prescription = new Prescription(
    appointmentId,
    patientId,
    doctorId,
    patientInfo,
    doctorInfo
);

Prescription.Medication medication = new Prescription.Medication(
    "Lisinopril",
    "10mg",
    "Once daily",
    "30 days",
    30
);
prescription.addMedication(medication);
```

## Validation Features

### Field-Level Validation
- Email format validation
- Phone number pattern matching
- Medical license format verification
- Password strength requirements
- Date range validation (birth dates, appointment dates)

### Business Logic Validation
- Appointment end time calculation
- Doctor working hours validation
- Patient age calculation
- Appointment conflict prevention

## Security Considerations

### Data Protection
- Password fields excluded from JSON serialization
- Sensitive medical data properly encapsulated
- Audit trails for compliance requirements

### HIPAA Compliance
- Patient data access controls
- Medical information segregation
- Comprehensive logging capabilities

## Performance Optimizations

### Database Efficiency
- Appropriate indexing on foreign keys and unique fields
- Lazy loading for related entities
- Optimized fetch strategies

### JSON Processing
- Efficient serialization with Jackson
- Minimal data transfer with non-null inclusion
- Proper relationship handling to prevent circular references

## Future Enhancements

### Planned Features
- Role-based field access control
- Advanced audit logging
- Document versioning for prescriptions
- Integration with external pharmacy systems
- Mobile-optimized API responses

### Scalability Considerations
- Connection pooling configuration
- Caching strategy implementation
- Database partitioning for large datasets
- Microservices decomposition readiness

This model foundation provides a robust, scalable, and maintainable base for the Smart Clinic Management System, supporting both current requirements and future growth.
