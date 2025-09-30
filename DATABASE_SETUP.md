# Smart Clinic Management System - Database Setup Guide

## Overview

This guide provides instructions for setting up and populating the MySQL and MongoDB databases for the Smart Clinic Management System.

## Prerequisites

- MySQL 8.0+ installed and running
- MongoDB 4.4+ installed and running  
- Java 17+
- Maven 3.6+

## Database Setup Instructions

### 1. MySQL Database Setup

#### Step 1: Create the Database
```sql
CREATE DATABASE cms;
USE cms;
```

#### Step 2: Configure Application Properties
Update `src/main/resources/application.properties` with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cms?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
```

#### Step 3: Run the Application
The application will automatically create the following tables using JPA/Hibernate:

- `admins` - System administrator accounts
- `doctors` - Medical professional profiles  
- `patients` - Patient demographic and medical information
- `appointments` - Appointment scheduling and management
- `doctor_availability` - Doctor schedule exceptions

#### Step 4: Insert Sample Data
Execute the sample data script located at `src/main/resources/db/sample_data.sql`:

```bash
mysql -u root -p cms < src/main/resources/db/sample_data.sql
```

### 2. MongoDB Database Setup

#### Step 1: Start MongoDB
Ensure MongoDB is running on the default port (27017).

#### Step 2: Configure Application Properties
MongoDB configuration in `application.properties`:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=cms_documents
spring.data.mongodb.auto-index-creation=true
```

#### Step 3: Automatic Data Population
Sample MongoDB documents are automatically inserted when the application starts via the `MongoDataInitializer` component.

## Sample Data Overview

### MySQL Tables

#### Admins (3 records)
- **admin** - Super Administrator with full permissions
- **dr_admin** - Medical Administrator with doctor/patient management access
- **support** - Support role with limited appointment access

#### Doctors (5 records)
- **Dr. Sarah Johnson** - Internal Medicine (Room 201)
- **Dr. Michael Smith** - Cardiology (Room 305)
- **Dr. Emily Williams** - Pediatrics (Room 102)
- **Dr. David Brown** - Orthopedic Surgery (Room 401)
- **Dr. Jennifer Davis** - Dermatology (Room 203)

#### Patients (6 records)
- **John Smith** - Hypertension patient with family history
- **Mary Jones** - Healthy young adult for annual checkups
- **Robert Wilson** - Diabetic patient with surgical history
- **Lisa Garcia** - Pregnant patient (second trimester)
- **James Taylor** - Pediatric patient with asthma
- **Susan Lee** - Adult patient with migraine history

#### Appointments (8 records)
- Various appointment types: Annual Physical, Consultation, Prenatal Checkup, etc.
- Status range: Scheduled, Confirmed
- Different durations: 30-90 minutes
- Dates in October 2025

#### Doctor Availability (6 records)
- Lunch breaks for various doctors
- Conference and surgery unavailability
- Personal days off
- Administrative meetings

### MongoDB Collections

#### Prescriptions (4 documents)
1. **John Smith - Hypertension/Diabetes**
   - Lisinopril 10mg daily
   - Metformin 500mg twice daily
   - Comprehensive medication details with interactions

2. **Lisa Garcia - Prenatal Care**
   - Prenatal multivitamin
   - Pregnancy-specific instructions

3. **James Taylor - Pediatric Asthma**
   - Albuterol rescue inhaler
   - Fluticasone preventive inhaler
   - Asthma management plan

4. **Susan Lee - Migraine Management**
   - Sumatriptan for acute treatment
   - Propranolol for prevention
   - Lifestyle modification instructions

## Database Schema Features

### MySQL Tables Features
- **Referential Integrity**: Foreign key relationships between patients, doctors, and appointments
- **Validation**: Check constraints for appointment dates, working hours
- **Audit Trail**: Creation and update timestamps on all entities
- **Soft Deletes**: `is_active` flags for data retention
- **Unique Constraints**: Email addresses, medical license numbers

### MongoDB Document Features
- **Flexible Schema**: Nested medication arrays with varying structures
- **Embedded Information**: Patient and doctor details within prescriptions
- **Digital Signatures**: Hash-based prescription validation
- **Pharmacy Integration**: Structured pharmacy information
- **Rich Metadata**: Comprehensive medication details including interactions

## Security Considerations

### Password Security
- All sample passwords are hashed using BCrypt
- Default password for all accounts: `password123`
- In production, enforce strong password policies

### Data Protection
- Patient medical information properly segregated
- Admin role-based access control implemented
- Audit trails for compliance requirements

## Development and Testing

### Running the Application
```bash
cd java-database-capstone
mvn spring-boot:run
```

### Viewing Database Contents

#### MySQL Commands
```sql
USE cms;
SHOW TABLES;
SELECT COUNT(*) FROM doctors;
SELECT COUNT(*) FROM patients;
SELECT COUNT(*) FROM appointments;
```

#### MongoDB Commands
```javascript
use cms_documents
db.prescriptions.count()
db.prescriptions.find().pretty()
```

### API Testing
Once the application is running, you can test the data through the REST endpoints:

- `GET /api/doctors` - List all doctors
- `GET /api/patients` - List all patients  
- `GET /api/appointments` - List all appointments
- `GET /api/prescriptions` - List all prescriptions

## Troubleshooting

### Common Issues

1. **MySQL Connection Failed**
   - Verify MySQL is running: `sudo systemctl status mysql`
   - Check credentials in application.properties
   - Ensure database `cms` exists

2. **MongoDB Connection Failed**
   - Verify MongoDB is running: `sudo systemctl status mongod`
   - Check MongoDB logs: `tail -f /var/log/mongodb/mongod.log`
   - Ensure port 27017 is accessible

3. **Tables Not Created**
   - Check Hibernate DDL setting: `spring.jpa.hibernate.ddl-auto=update`
   - Review application startup logs for errors
   - Verify model classes are in correct package

4. **Sample Data Not Inserted**
   - For MySQL: Run sample_data.sql script manually
   - For MongoDB: Check MongoDataInitializer component logs
   - Verify data doesn't already exist (initializers skip if data present)

## Performance Optimization

### MySQL Optimization
- Indexes automatically created on primary keys and foreign keys
- Consider additional indexes on frequently queried fields:
  ```sql
  CREATE INDEX idx_appointment_date ON appointments(appointment_date);
  CREATE INDEX idx_doctor_specialization ON doctors(specialization);
  ```

### MongoDB Optimization
- Automatic index creation enabled in application.properties
- Consider compound indexes for complex queries:
  ```javascript
  db.prescriptions.createIndex({patientId: 1, prescriptionDate: -1})
  db.prescriptions.createIndex({doctorId: 1, status: 1})
  ```

## Data Backup and Recovery

### MySQL Backup
```bash
mysqldump -u root -p cms > cms_backup.sql
```

### MongoDB Backup
```bash
mongodump --db cms_documents --out backup/
```

### Restore Commands
```bash
# MySQL
mysql -u root -p cms < cms_backup.sql

# MongoDB
mongorestore backup/
```

This database setup provides a comprehensive foundation for the Smart Clinic Management System with realistic sample data for development and testing purposes.
