-- Smart Clinic Management System - Sample Data
-- This script inserts sample data compatible with the entity structure

-- Insert sample doctors with proper entity structure
INSERT INTO doctors (email, password_hash, first_name, last_name, specialization, license_number, phone_number, bio, years_experience, consultation_fee, office_location, working_hours_start, working_hours_end, is_active, created_at, updated_at) VALUES
('dr.adams@example.com', 'pass12345', 'Emily', 'Adams', 'Cardiologist', 'MD001234', '+15551012020', 'Experienced cardiologist specializing in heart diseases', 15, 200.00, 'Room 101', '09:00:00', '17:00:00', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('dr.johnson@example.com', 'secure4567', 'Mark', 'Johnson', 'Neurologist', 'MD002345', '+15552023030', 'Neurologist with expertise in brain disorders', 12, 250.00, 'Room 102', '10:00:00', '18:00:00', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('dr.lee@example.com', 'leePass987', 'Sarah', 'Lee', 'Orthopedist', 'MD003456', '+15553034040', 'Orthopedic surgeon specializing in bone and joint care', 10, 180.00, 'Room 103', '08:00:00', '16:00:00', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('dr.wilson@example.com', 'w!ls0nPwd', 'Tom', 'Wilson', 'Pediatrician', 'MD004567', '+15554045050', 'Pediatrician dedicated to children healthcare', 8, 150.00, 'Room 104', '09:00:00', '17:00:00', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('dr.brown@example.com', 'brownie123', 'Alice', 'Brown', 'Dermatologist', 'MD005678', '+15555056060', 'Dermatologist focusing on skin health and treatments', 7, 170.00, 'Room 105', '10:00:00', '18:00:00', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample patients with proper entity structure
INSERT INTO patients (email, password_hash, first_name, last_name, phone_number, date_of_birth, gender, address, emergency_contact_name, emergency_contact_phone, blood_type, is_active, created_at, updated_at) VALUES
('jane.doe@example.com', 'passJane1', 'Jane', 'Doe', '+18881111111', '1985-03-15', 'FEMALE', '101 Oak St, Cityville', 'John Doe', '+18881111112', 'A+', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('john.smith@example.com', 'smithSecure', 'John', 'Smith', '+18882222222', '1978-07-22', 'MALE', '202 Maple Rd, Townsville', 'Mary Smith', '+18882222223', 'O+', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('emily.rose@example.com', 'emilyPass99', 'Emily', 'Rose', '+18883333333', '1992-11-08', 'FEMALE', '303 Pine Ave, Villageton', 'David Rose', '+18883333334', 'B+', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('michael.j@example.com', 'airmj23', 'Michael', 'Jordan', '+18884444444', '1963-02-17', 'MALE', '404 Birch Ln, Metropolis', 'Juanita Jordan', '+18884444445', 'AB+', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('olivia.m@example.com', 'moonshine12', 'Olivia', 'Moon', '+18885555555', '1990-05-30', 'FEMALE', '505 Cedar Blvd, Springfield', 'Luna Moon', '+18885555556', 'A-', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert doctor availability slots
INSERT INTO doctor_availability (doctor_id, date, start_time, end_time, availability_type, is_recurring, created_at) VALUES
(1, '2025-10-02', '09:00:00', '10:00:00', 'AVAILABLE', false, CURRENT_TIMESTAMP),
(1, '2025-10-02', '10:00:00', '11:00:00', 'AVAILABLE', false, CURRENT_TIMESTAMP),
(1, '2025-10-02', '14:00:00', '15:00:00', 'AVAILABLE', false, CURRENT_TIMESTAMP),
(2, '2025-10-02', '10:00:00', '11:00:00', 'AVAILABLE', false, CURRENT_TIMESTAMP),
(2, '2025-10-02', '11:00:00', '12:00:00', 'AVAILABLE', false, CURRENT_TIMESTAMP),
(2, '2025-10-02', '15:00:00', '16:00:00', 'AVAILABLE', false, CURRENT_TIMESTAMP),
(3, '2025-10-02', '08:00:00', '09:00:00', 'AVAILABLE', false, CURRENT_TIMESTAMP),
(3, '2025-10-02', '11:00:00', '12:00:00', 'AVAILABLE', false, CURRENT_TIMESTAMP),
(3, '2025-10-02', '14:00:00', '15:00:00', 'AVAILABLE', false, CURRENT_TIMESTAMP),
(4, '2025-10-02', '09:00:00', '10:00:00', 'AVAILABLE', false, CURRENT_TIMESTAMP),
(4, '2025-10-02', '15:00:00', '16:00:00', 'AVAILABLE', false, CURRENT_TIMESTAMP),
(5, '2025-10-02', '10:00:00', '11:00:00', 'AVAILABLE', false, CURRENT_TIMESTAMP),
(5, '2025-10-02', '14:00:00', '15:00:00', 'AVAILABLE', false, CURRENT_TIMESTAMP);

-- Insert sample appointments with proper entity structure
INSERT INTO appointments (doctor_id, patient_id, appointment_date, appointment_time, status, appointment_type, reason_for_visit, duration_minutes, created_at, updated_at) VALUES
(1, 1, '2025-10-15', '09:00:00', 'SCHEDULED', 'Consultation', 'Chest pain evaluation', 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, '2025-10-15', '10:00:00', 'SCHEDULED', 'Follow-up', 'Heart checkup', 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, '2025-10-16', '10:00:00', 'SCHEDULED', 'Consultation', 'Headache concerns', 45, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, '2025-10-16', '11:00:00', 'CONFIRMED', 'Examination', 'Neurological assessment', 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 5, '2025-10-17', '08:00:00', 'SCHEDULED', 'Surgery Consultation', 'Knee pain evaluation', 45, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
