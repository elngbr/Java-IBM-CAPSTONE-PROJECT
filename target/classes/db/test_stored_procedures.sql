-- Smart Clinic Management System - Stored Procedures Test Script
-- Lab: Adding Stored Procedures - Execution Commands
-- Date: September 30, 2024

-- =============================================================================
-- INSTRUCTIONS FOR EXECUTION
-- =============================================================================
-- 1. Ensure MySQL is running and the cms database exists with sample data
-- 2. Connect to MySQL: mysql -u root -p
-- 3. Use the database: USE cms;
-- 4. Execute the stored procedures script first: source stored_procedures.sql;
-- 5. Then run the test commands below

-- =============================================================================
-- Test 1: Daily Appointment Report by Doctor
-- =============================================================================
-- Purpose: Generate report for all appointments on April 15, 2025
-- Expected: List of appointments grouped by doctor with patient details

CALL GetDailyAppointmentReportByDoctor('2025-04-15');

-- Expected Output Format:
-- +---------------+---------------------+-------------+--------------+---------------+
-- | doctor_name   | appointment_time    | status      | patient_name | patient_phone |
-- +---------------+---------------------+-------------+--------------+---------------+
-- | Dr. X         | 2025-04-15 09:00:00 | Scheduled   | Patient Y    | 555-0123     |
-- +---------------+---------------------+-------------+--------------+---------------+

-- =============================================================================
-- Test 2: Doctor with Most Patients by Month
-- =============================================================================
-- Purpose: Find doctor who saw most patients in April 2025
-- Expected: Doctor ID and patient count for highest volume doctor

CALL GetDoctorWithMostPatientsByMonth(4, 2025);

-- Expected Output Format:
-- +-----------+---------------+
-- | doctor_id | patients_seen |
-- +-----------+---------------+
-- |         1 |             5 |
-- +-----------+---------------+

-- =============================================================================
-- Test 3: Doctor with Most Patients by Year
-- =============================================================================
-- Purpose: Find doctor who saw most patients in 2025
-- Expected: Doctor ID and patient count for highest volume doctor in the year

CALL GetDoctorWithMostPatientsByYear(2025);

-- Expected Output Format:
-- +-----------+---------------+
-- | doctor_id | patients_seen |
-- +-----------+---------------+
-- |         1 |            12 |
-- +-----------+---------------+

-- =============================================================================
-- Verification Queries
-- =============================================================================

-- Check that procedures were created successfully
SHOW PROCEDURE STATUS WHERE Db = 'cms';

-- Verify sample data exists for testing
SELECT COUNT(*) as total_appointments FROM appointment;
SELECT COUNT(*) as total_doctors FROM doctor;
SELECT COUNT(*) as total_patients FROM patient;

-- Check appointment dates to verify test data coverage
SELECT 
    DATE(appointment_time) as appointment_date,
    COUNT(*) as appointments_count
FROM appointment 
GROUP BY DATE(appointment_time)
ORDER BY appointment_date;

-- =============================================================================
-- Additional Test Scenarios (Optional)
-- =============================================================================

-- Test with different dates that exist in sample data
-- CALL GetDailyAppointmentReportByDoctor('2024-12-15');
-- CALL GetDailyAppointmentReportByDoctor('2024-12-16');

-- Test with different months
-- CALL GetDoctorWithMostPatientsByMonth(12, 2024);
-- CALL GetDoctorWithMostPatientsByMonth(1, 2025);

-- Test with different years
-- CALL GetDoctorWithMostPatientsByYear(2024);

-- =============================================================================
-- Cleanup (Optional - only if you need to recreate procedures)
-- =============================================================================

-- DROP PROCEDURE IF EXISTS GetDailyAppointmentReportByDoctor;
-- DROP PROCEDURE IF EXISTS GetDoctorWithMostPatientsByMonth;
-- DROP PROCEDURE IF EXISTS GetDoctorWithMostPatientsByYear;
