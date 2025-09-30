# Lab: Adding Stored Procedures - Smart Clinic Management System

## Overview
This lab demonstrates how to create stored procedures for generating reports in the Smart Clinic Management System. The procedures automate report generation using SQL joins and aggregation functions.

## Learning Objectives
After completing this lab, you will be able to:
- Write stored procedures to generate daily and summary reports
- Use SQL joins to query across multiple tables
- Practice executing stored procedures with parameters

## Key Terms
- **Stored Procedure**: A saved collection of SQL statements that can be executed with a single call
- **SQL JOIN**: Combines rows from two or more tables based on related columns
- **DELIMITER**: Command used in MySQL to change statement delimiter for multi-statement procedures
- **CALL**: SQL command used to invoke or run a stored procedure
- **Input Parameters**: Variables passed into a procedure when called
- **GROUP BY**: Clause used to group rows with same values in specified columns
- **ORDER BY**: Used to sort result set based on one or more columns
- **Aggregation Function**: Functions like COUNT() that operate on sets of values

## Stored Procedures Implementation

### 1. Daily Appointment Report by Doctor
**Procedure**: `GetDailyAppointmentReportByDoctor`

**Purpose**: Generates a report listing all appointments on a specific date, grouped by doctor. Displays doctor name, appointment time, status, patient name, and phone number.

**Parameters**:
- `report_date` (DATE): The date for which to generate the report

**Usage**:
```sql
CALL GetDailyAppointmentReportByDoctor('2025-04-15');
```

**Expected Output**:
```
+---------------+---------------------+-------------+--------------+---------------+
| doctor_name   | appointment_time    | status      | patient_name | patient_phone |
+---------------+---------------------+-------------+--------------+---------------+
| Dr. Smith     | 2025-04-15 09:00:00 | Scheduled   | John Doe     | 555-0123     |
| Dr. Smith     | 2025-04-15 14:00:00 | Completed   | Jane Smith   | 555-0456     |
+---------------+---------------------+-------------+--------------+---------------+
```

### 2. Doctor with Most Patients by Month
**Procedure**: `GetDoctorWithMostPatientsByMonth`

**Purpose**: Identifies the doctor who saw the most patients in a given month and year. Helps clinic managers understand patient load distribution.

**Parameters**:
- `input_month` (INT): Month number (1-12)
- `input_year` (INT): Year

**Usage**:
```sql
CALL GetDoctorWithMostPatientsByMonth(4, 2025);
```

**Expected Output**:
```
+-----------+---------------+
| doctor_id | patients_seen |
+-----------+---------------+
|         1 |             8 |
+-----------+---------------+
```

### 3. Doctor with Most Patients by Year
**Procedure**: `GetDoctorWithMostPatientsByYear`

**Purpose**: Identifies the doctor with the most patients in a given year. Useful for annual performance summaries.

**Parameters**:
- `input_year` (INT): Year

**Usage**:
```sql
CALL GetDoctorWithMostPatientsByYear(2025);
```

**Expected Output**:
```
+-----------+---------------+
| doctor_id | patients_seen |
+-----------+---------------+
|         1 |            24 |
+-----------+---------------+
```

## Setup Instructions

### Prerequisites
1. MySQL 8.0+ installed and running
2. CMS database created with sample data (from previous lab)
3. All tables populated with test data

### Installation Steps
1. Connect to MySQL:
   ```bash
   mysql -u root -p
   ```

2. Select the CMS database:
   ```sql
   USE cms;
   ```

3. Execute the stored procedures script:
   ```bash
   source src/main/resources/db/stored_procedures.sql;
   ```

4. Verify procedures were created:
   ```sql
   SHOW PROCEDURE STATUS WHERE Db = 'cms';
   ```

## Testing the Procedures

### Test Execution Commands
Run each procedure with the lab-specified parameters:

```sql
-- Test 1: Daily report for April 15, 2025
CALL GetDailyAppointmentReportByDoctor('2025-04-15');

-- Test 2: Most patients by month (April 2025)
CALL GetDoctorWithMostPatientsByMonth(4, 2025);

-- Test 3: Most patients by year (2025)
CALL GetDoctorWithMostPatientsByYear(2025);
```

### Verification Queries
Check data coverage and procedure status:

```sql
-- Verify sample data exists
SELECT COUNT(*) as total_appointments FROM appointment;
SELECT COUNT(*) as total_doctors FROM doctor;

-- Check appointment date coverage
SELECT 
    DATE(appointment_time) as appointment_date,
    COUNT(*) as appointments_count
FROM appointment 
GROUP BY DATE(appointment_time)
ORDER BY appointment_date;
```

## Technical Implementation Details

### SQL Joins Used
- **INNER JOIN**: Links appointments with doctors and patients
- **Foreign Key Relationships**: 
  - `appointment.doctor_id` → `doctor.id`
  - `appointment.patient_id` → `patient.id`

### Aggregation Functions
- **COUNT()**: Counts number of patients per doctor
- **GROUP BY**: Groups results by doctor_id
- **ORDER BY**: Sorts results by patient count (DESC) and appointment time

### Date Functions
- **DATE()**: Extracts date part from datetime
- **MONTH()**: Extracts month from datetime
- **YEAR()**: Extracts year from datetime

## File Structure
```
src/main/resources/db/
├── stored_procedures.sql          # Main stored procedures definitions
├── test_stored_procedures.sql     # Test commands and examples
├── sample_data.sql               # Base data (from previous lab)
└── mongodb_sample_data.js        # MongoDB data (from previous lab)
```

## Lab Assignment Requirements

### Submission Requirements
For the assignment, submit the complete output from each stored procedure execution:

1. **Terminal Output**: Save the complete output from all three procedure calls
2. **Screenshots**: Capture the MySQL command line interface showing the results
3. **Documentation**: Include this documentation file with your submission

### Expected Deliverables
- ✅ All three stored procedures created successfully
- ✅ Test executions completed with captured output
- ✅ Verification queries run and documented
- ✅ Code committed to GitHub repository

## Troubleshooting

### Common Issues
1. **Procedure Already Exists**: Use `DROP PROCEDURE IF EXISTS` before recreating
2. **No Data Found**: Ensure sample data is loaded and dates match test parameters
3. **Permission Denied**: Verify MySQL user has CREATE ROUTINE privileges
4. **Syntax Errors**: Check DELIMITER commands and END statements

### Debug Commands
```sql
-- Check if procedures exist
SHOW PROCEDURE STATUS WHERE Db = 'cms';

-- View procedure definition
SHOW CREATE PROCEDURE GetDailyAppointmentReportByDoctor;

-- Check table data
SELECT * FROM appointment LIMIT 5;
```

## Next Steps
After completing this lab:
1. Integrate stored procedures into Spring Boot application
2. Create REST API endpoints that call these procedures
3. Develop frontend reports using the procedure data
4. Add additional analytical procedures as needed

## Conclusion
This lab successfully demonstrates:
- Creating stored procedures for automated reporting
- Using SQL joins to combine data from multiple tables
- Implementing parameterized queries for flexible reporting
- Best practices for database procedure development

The stored procedures provide essential reporting capabilities for the Smart Clinic Management System and form the foundation for more advanced analytics and business intelligence features.
