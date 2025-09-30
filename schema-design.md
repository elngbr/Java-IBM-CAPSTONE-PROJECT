# Smart Clinic Management System - Database Schema Design

## Overview

The Smart Clinic Management System employs a hybrid database architecture that leverages the strengths of both relational and document-based storage systems. This approach ensures optimal performance, scalability, and data integrity while accommodating both structured and unstructured data requirements.

### Design Philosophy

- **MySQL**: Used for structured, relational data requiring ACID compliance, complex relationships, and referential integrity
- **MongoDB**: Used for flexible, document-based data that may evolve over time and doesn't require strict relational constraints

---

## MySQL Database Design

### Database: `smart_clinic_db`

The MySQL database handles core operational data with well-defined relationships and strict consistency requirements.

### Table 1: `patients`
**Purpose**: Store patient demographic and account information

```sql
CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    address TEXT,
    emergency_contact_name VARCHAR(200),
    emergency_contact_phone VARCHAR(20),
    blood_type VARCHAR(5),
    allergies TEXT,
    medical_history TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);
```

**Key Constraints:**
- `email`: Unique identifier for patient login
- `password_hash`: Encrypted password storage
- `phone_number`: Required for appointment confirmations
- `date_of_birth`: Required for age-based medical decisions
- `is_active`: Soft delete functionality

---

### Table 2: `doctors`
**Purpose**: Store doctor profiles, credentials, and availability information

```sql
CREATE TABLE doctors (
    doctor_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    specialization VARCHAR(200) NOT NULL,
    license_number VARCHAR(50) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL,
    bio TEXT,
    years_experience INT DEFAULT 0,
    consultation_fee DECIMAL(10,2),
    office_location VARCHAR(200),
    working_hours_start TIME DEFAULT '09:00:00',
    working_hours_end TIME DEFAULT '17:00:00',
    profile_image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);
```

**Key Constraints:**
- `license_number`: Unique medical license verification
- `specialization`: Medical specialty for patient matching
- `consultation_fee`: Pricing information
- `working_hours_*`: Default availability window
- `is_active`: For managing doctor availability

---

### Table 3: `appointments`
**Purpose**: Manage appointment scheduling and status tracking

```sql
CREATE TABLE appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    duration_minutes INT DEFAULT 60,
    status ENUM('Scheduled', 'Confirmed', 'In-Progress', 'Completed', 'Cancelled', 'No-Show') DEFAULT 'Scheduled',
    appointment_type VARCHAR(100) DEFAULT 'Consultation',
    reason_for_visit TEXT,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE,
    
    -- Prevent double booking for doctors
    UNIQUE KEY unique_doctor_slot (doctor_id, appointment_date, appointment_time),
    
    -- Ensure appointment is in the future (can be modified by triggers)
    CHECK (appointment_date >= CURDATE())
);
```

**Key Constraints:**
- Foreign keys ensure referential integrity
- Unique constraint prevents doctor double-booking
- Status enum tracks appointment lifecycle
- Duration defaults to 1 hour as per requirements

---

### Table 4: `admins`
**Purpose**: System administrator accounts and permissions

```sql
CREATE TABLE admins (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    role ENUM('Super Admin', 'Admin', 'Support') DEFAULT 'Admin',
    permissions JSON, -- Store specific permissions as JSON
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);
```

**Key Constraints:**
- `username`: Unique login identifier separate from email
- `role`: Administrative hierarchy
- `permissions`: JSON field for flexible permission management
- `last_login`: Security auditing

---

### Table 5: `doctor_availability`
**Purpose**: Manage doctor schedule exceptions and unavailability

```sql
CREATE TABLE doctor_availability (
    availability_id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id INT NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    availability_type ENUM('Available', 'Unavailable', 'Break') DEFAULT 'Unavailable',
    reason VARCHAR(200),
    is_recurring BOOLEAN DEFAULT FALSE,
    recurrence_pattern VARCHAR(50), -- 'weekly', 'monthly', etc.
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE,
    
    -- Ensure logical time ordering
    CHECK (end_time > start_time)
);
```

**Key Constraints:**
- Tracks exceptions to default working hours
- Supports recurring patterns (weekly meetings, etc.)
- Reason field for transparency

---

### Table 6: `appointment_history`
**Purpose**: Audit trail for appointment changes and cancellations

```sql
CREATE TABLE appointment_history (
    history_id INT AUTO_INCREMENT PRIMARY KEY,
    appointment_id INT NOT NULL,
    action ENUM('Created', 'Updated', 'Cancelled', 'Completed') NOT NULL,
    changed_by_user_id INT,
    changed_by_user_type ENUM('Patient', 'Doctor', 'Admin') NOT NULL,
    old_values JSON,
    new_values JSON,
    change_reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id) ON DELETE CASCADE
);
```

**Key Constraints:**
- Complete audit trail for compliance
- JSON fields store before/after values
- User attribution for accountability

---

## MongoDB Database Design

### Database: `smart_clinic_documents`

The MongoDB database handles flexible, evolving document structures that don't require strict relational constraints.

### Collection 1: `prescriptions`
**Purpose**: Store detailed prescription information with flexible medication data

```json
{
  "_id": ObjectId("652a1b2c3d4e5f6789abcdef"),
  "appointmentId": 1001,
  "patientId": 2001,
  "doctorId": 3001,
  "prescriptionDate": ISODate("2025-09-30T14:30:00Z"),
  "patientInfo": {
    "name": "John Smith",
    "age": 45,
    "weight": 180,
    "allergies": ["Penicillin", "Shellfish"]
  },
  "doctorInfo": {
    "name": "Dr. Sarah Johnson",
    "licenseNumber": "MD123456",
    "specialization": "Internal Medicine"
  },
  "diagnosis": {
    "primary": "Hypertension",
    "secondary": ["Type 2 Diabetes", "Obesity"],
    "icdCodes": ["I10", "E11.9", "E66.9"]
  },
  "medications": [
    {
      "medicationName": "Lisinopril",
      "genericName": "Lisinopril",
      "dosage": "10mg",
      "frequency": "Once daily",
      "duration": "30 days",
      "quantity": 30,
      "refills": 2,
      "instructions": "Take with or without food. Monitor blood pressure regularly.",
      "sideEffects": ["Dizziness", "Dry cough"],
      "interactions": ["NSAIDs", "Potassium supplements"]
    },
    {
      "medicationName": "Metformin",
      "genericName": "Metformin HCl",
      "dosage": "500mg",
      "frequency": "Twice daily with meals",
      "duration": "90 days",
      "quantity": 180,
      "refills": 3,
      "instructions": "Take with food to reduce stomach upset. Monitor blood glucose levels.",
      "sideEffects": ["Nausea", "Diarrhea", "Metallic taste"],
      "interactions": ["Alcohol", "Contrast dyes"]
    }
  ],
  "additionalInstructions": [
    "Follow up in 4 weeks for blood pressure check",
    "Continue low-sodium diet",
    "Exercise 30 minutes daily",
    "Monitor weight weekly"
  ],
  "pharmacyInfo": {
    "name": "Central Pharmacy",
    "address": "123 Main St, City, State 12345",
    "phone": "(555) 123-4567",
    "faxSent": true,
    "sentAt": ISODate("2025-09-30T14:35:00Z")
  },
  "digitalSignature": {
    "signed": true,
    "signedAt": ISODate("2025-09-30T14:32:00Z"),
    "signatureHash": "sha256:a1b2c3d4e5f6789012345678901234567890abcdef"
  },
  "status": "Active",
  "createdAt": ISODate("2025-09-30T14:30:00Z"),
  "updatedAt": ISODate("2025-09-30T14:35:00Z")
}
```

**Design Rationale:**
- Flexible medication array allows for multiple prescriptions
- Embedded patient/doctor info for quick access without joins
- Nested medication details accommodate varying prescription complexity
- Digital signature support for legal compliance
- Pharmacy integration fields for automated processing

---

### Collection 2: `patient_feedback`
**Purpose**: Store patient reviews and feedback with varying structures

```json
{
  "_id": ObjectId("652a1b2c3d4e5f6789abcde0"),
  "appointmentId": 1001,
  "patientId": 2001,
  "doctorId": 3001,
  "feedbackType": "Post-Appointment Survey",
  "ratings": {
    "overall": 5,
    "doctorCommunication": 5,
    "waitTime": 4,
    "facilityCleaniness": 5,
    "staffCourtesy": 4,
    "appointmentScheduling": 5
  },
  "feedback": {
    "positiveComments": [
      "Dr. Johnson was very thorough and explained everything clearly",
      "The office staff was friendly and helpful",
      "Appointment was on time"
    ],
    "improvementSuggestions": [
      "Could use more comfortable waiting room chairs",
      "Would appreciate longer appointment slots"
    ],
    "wouldRecommend": true,
    "returnPatient": true
  },
  "sentiment": {
    "overallSentiment": "Positive",
    "confidence": 0.87,
    "analyzedAt": ISODate("2025-09-30T16:00:00Z")
  },
  "responseMetadata": {
    "surveyVersion": "2.1",
    "responseTime": 180, // seconds
    "deviceType": "Mobile",
    "ipAddress": "192.168.1.100",
    "userAgent": "Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X)"
  },
  "followUpActions": [
    {
      "action": "Thank you email sent",
      "completedAt": ISODate("2025-09-30T17:00:00Z"),
      "completedBy": "system"
    }
  ],
  "isAnonymous": false,
  "isPublicReview": true,
  "createdAt": ISODate("2025-09-30T15:30:00Z"),
  "updatedAt": ISODate("2025-09-30T17:00:00Z")
}
```

**Design Rationale:**
- Flexible rating system accommodates different survey types
- Sentiment analysis integration for automated insights
- Metadata tracking for survey improvement
- Follow-up action tracking for patient relations

---

### Collection 3: `system_logs`
**Purpose**: Store application logs, audit trails, and system events

```json
{
  "_id": ObjectId("652a1b2c3d4e5f6789abcde1"),
  "timestamp": ISODate("2025-09-30T10:15:30.123Z"),
  "logLevel": "INFO",
  "service": "AppointmentService",
  "action": "APPOINTMENT_CREATED",
  "userId": 2001,
  "userType": "Patient",
  "sessionId": "sess_abc123def456",
  "appointmentDetails": {
    "appointmentId": 1001,
    "doctorId": 3001,
    "scheduledDate": "2025-10-15",
    "scheduledTime": "14:30:00"
  },
  "requestMetadata": {
    "ipAddress": "192.168.1.100",
    "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36",
    "endpoint": "/api/appointments",
    "method": "POST",
    "responseTime": 245, // milliseconds
    "statusCode": 201
  },
  "systemMetrics": {
    "memoryUsage": "1.2GB",
    "cpuUsage": "15%",
    "activeConnections": 45,
    "databaseConnections": 8
  },
  "location": {
    "city": "New York",
    "country": "USA",
    "timezone": "America/New_York"
  },
  "tags": ["appointment", "creation", "patient-action"],
  "correlationId": "req_789xyz123abc",
  "environment": "production"
}
```

**Design Rationale:**
- Comprehensive logging for debugging and auditing
- Flexible structure accommodates different log types
- Performance metrics for system monitoring
- Correlation IDs for distributed tracing

---

### Collection 4: `clinic_communications`
**Purpose**: Store messages, notifications, and communication history

```json
{
  "_id": ObjectId("652a1b2c3d4e5f6789abcde2"),
  "communicationType": "Appointment Reminder",
  "senderId": 0, // System-generated
  "senderType": "System",
  "recipientId": 2001,
  "recipientType": "Patient",
  "appointmentId": 1001,
  "channels": [
    {
      "type": "email",
      "address": "john.smith@email.com",
      "sentAt": ISODate("2025-09-29T09:00:00Z"),
      "status": "Delivered",
      "messageId": "msg_email_abc123",
      "openedAt": ISODate("2025-09-29T09:15:00Z"),
      "clickedAt": null
    },
    {
      "type": "sms",
      "phoneNumber": "+1-555-123-4567",
      "sentAt": ISODate("2025-09-29T09:00:00Z"),
      "status": "Delivered",
      "messageId": "msg_sms_def456",
      "deliveredAt": ISODate("2025-09-29T09:01:00Z")
    }
  ],
  "messageContent": {
    "subject": "Appointment Reminder - Tomorrow at 2:30 PM",
    "bodyText": "Dear John, this is a reminder that you have an appointment with Dr. Sarah Johnson tomorrow (Oct 15, 2025) at 2:30 PM. Please arrive 15 minutes early. If you need to reschedule, please call (555) 123-4567.",
    "bodyHtml": "<html><body><p>Dear John,</p><p>This is a reminder that you have an appointment with <strong>Dr. Sarah Johnson</strong> tomorrow (Oct 15, 2025) at <strong>2:30 PM</strong>.</p><p>Please arrive 15 minutes early.</p><p>If you need to reschedule, please call <a href='tel:+15551234567'>(555) 123-4567</a>.</p></body></html>",
    "templateId": "appointment_reminder_v2",
    "templateVariables": {
      "patientName": "John",
      "doctorName": "Dr. Sarah Johnson",
      "appointmentDate": "Oct 15, 2025",
      "appointmentTime": "2:30 PM",
      "clinicPhone": "(555) 123-4567"
    }
  },
  "scheduledFor": ISODate("2025-09-29T09:00:00Z"),
  "priority": "Normal",
  "retryPolicy": {
    "maxRetries": 3,
    "retryInterval": 300, // seconds
    "currentRetry": 0
  },
  "compliance": {
    "gdprConsent": true,
    "hipaaCompliant": true,
    "optOutRespected": false
  },
  "metrics": {
    "deliveryTime": 1.2, // seconds
    "cost": 0.003, // USD
    "provider": "AWS SES"
  },
  "createdAt": ISODate("2025-09-28T10:00:00Z"),
  "updatedAt": ISODate("2025-09-29T09:01:00Z")
}
```

**Design Rationale:**
- Multi-channel communication support (email, SMS, push)
- Template-based messaging for consistency
- Delivery tracking and metrics
- Compliance and consent management

---

## Design Justifications and Architecture Decisions

### Hybrid Database Approach Rationale

1. **MySQL for Core Business Logic**:
   - **Referential Integrity**: Patient-Doctor-Appointment relationships require strict consistency
   - **ACID Compliance**: Financial transactions and appointment scheduling need transactional guarantees
   - **Mature Ecosystem**: Well-established tooling, backup solutions, and monitoring
   - **SQL Familiarity**: Team expertise and reporting capabilities

2. **MongoDB for Flexible Documents**:
   - **Schema Evolution**: Prescriptions and feedback structures may change over time
   - **Nested Data**: Medical prescriptions have complex, nested medication information
   - **Performance**: Fast reads for log aggregation and communication history
   - **Scalability**: Horizontal scaling for high-volume logging and messaging

### Key Design Decisions

1. **Soft Deletes**: Using `is_active` flags instead of hard deletes for audit trails and data recovery

2. **JSON Fields in MySQL**: Strategic use of JSON columns for flexible permissions and change tracking

3. **Timestamp Standardization**: Consistent `created_at` and `updated_at` fields across all entities

4. **Foreign Key Constraints**: Strict referential integrity in MySQL with CASCADE options for data consistency

5. **Document Embedding vs Referencing**: Embedding frequently-accessed data (patient/doctor info in prescriptions) while referencing by ID for relationships

6. **Audit Trail Strategy**: Combination of MySQL history tables and MongoDB logs for comprehensive tracking

### Performance Considerations

1. **Indexing Strategy**:
   - MySQL: Primary keys, foreign keys, unique constraints on email/license numbers
   - MongoDB: Compound indexes on frequently queried fields (patientId + date, appointmentId)

2. **Data Archiving**: 
   - Older appointments and logs can be archived to separate collections/tables
   - Partition strategy for appointment_history by date ranges

3. **Caching Layer**: 
   - Redis integration for session management and frequently accessed doctor availability
   - Application-level caching for read-heavy operations

### Security and Compliance

1. **Data Encryption**: 
   - At-rest encryption for both databases
   - Field-level encryption for sensitive medical data

2. **Access Control**:
   - Role-based access control (RBAC) with granular permissions
   - Audit logging for all data access and modifications

3. **HIPAA Compliance**:
   - Patient data segregation and access controls
   - Comprehensive audit trails for all medical data access
   - Data retention and purging policies

This hybrid database design provides a robust foundation for the Smart Clinic Management System, balancing performance, scalability, and data integrity while accommodating both current requirements and future growth.
