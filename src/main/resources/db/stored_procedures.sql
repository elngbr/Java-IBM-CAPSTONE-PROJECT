-- Smart Clinic Management System - Stored Procedures
-- Lab: Adding Stored Procedures
-- Date: September 30, 2024

-- Use the CMS database
USE cms;

-- =============================================================================
-- Stored Procedure 1: Daily Appointment Report by Doctor
-- =============================================================================
-- Purpose: Generates a report listing all appointments on a specific date, grouped by doctor
-- Parameters: report_date (DATE) - The date for which to generate the report
-- Output: Doctor name, appointment time, status, patient name, and patient phone

DELIMITER $$
CREATE PROCEDURE GetDailyAppointmentReportByDoctor(
    IN report_date DATE
)
BEGIN
    SELECT 
        d.name AS doctor_name,
        a.appointment_time,
        a.status,
        p.name AS patient_name,
        p.phone AS patient_phone
    FROM 
        appointment a
    JOIN 
        doctor d ON a.doctor_id = d.id
    JOIN 
        patient p ON a.patient_id = p.id
    WHERE 
        DATE(a.appointment_time) = report_date
    ORDER BY 
        d.name, a.appointment_time;
END$$
DELIMITER ;

-- =============================================================================
-- Stored Procedure 2: Doctor with Most Patients by Month
-- =============================================================================
-- Purpose: Identifies the doctor who saw the most patients in a given month and year
-- Parameters: input_month (INT), input_year (INT)
-- Output: Doctor ID and count of patients seen

DELIMITER $$
CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(
    IN input_month INT, 
    IN input_year INT
)
BEGIN
    SELECT
        doctor_id, 
        COUNT(patient_id) AS patients_seen
    FROM
        appointment
    WHERE
        MONTH(appointment_time) = input_month 
        AND YEAR(appointment_time) = input_year
    GROUP BY
        doctor_id
    ORDER BY
        patients_seen DESC
    LIMIT 1;
END $$
DELIMITER ;

-- =============================================================================
-- Stored Procedure 3: Doctor with Most Patients by Year
-- =============================================================================
-- Purpose: Identifies the doctor with the most patients in a given year
-- Parameters: input_year (INT)
-- Output: Doctor ID and count of patients seen

DELIMITER $$
CREATE PROCEDURE GetDoctorWithMostPatientsByYear(
    IN input_year INT
)
BEGIN
    SELECT
        doctor_id, 
        COUNT(patient_id) AS patients_seen
    FROM
        appointment
    WHERE
        YEAR(appointment_time) = input_year
    GROUP BY
        doctor_id
    ORDER BY
        patients_seen DESC
    LIMIT 1;
END $$
DELIMITER ;

-- =============================================================================
-- Test Procedure Calls (As specified in the lab)
-- =============================================================================

-- Test 1: Daily Appointment Report for April 15, 2025
-- CALL GetDailyAppointmentReportByDoctor('2025-04-15');

-- Test 2: Doctor with Most Patients in April 2025
-- CALL GetDoctorWithMostPatientsByMonth(4, 2025);

-- Test 3: Doctor with Most Patients in 2025
-- CALL GetDoctorWithMostPatientsByYear(2025);

-- =============================================================================
-- Utility Queries for Verification
-- =============================================================================

-- Check if procedures were created successfully
-- SHOW PROCEDURE STATUS WHERE Db = 'cms';

-- View procedure definitions
-- SHOW CREATE PROCEDURE GetDailyAppointmentReportByDoctor;
-- SHOW CREATE PROCEDURE GetDoctorWithMostPatientsByMonth;
-- SHOW CREATE PROCEDURE GetDoctorWithMostPatientsByYear;
