-- Smart Clinic Management System - Sample Data
-- This script inserts sample data into all MySQL tables

USE cms;

-- Insert sample admin users
INSERT INTO admins (username, email, password_hash, first_name, last_name, role, permissions, is_active) VALUES
('admin', 'admin@smartclinic.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdJMReUHlpSLTEiHQ2XrcXztA/K', 'System', 'Administrator', 'SUPER_ADMIN', '{"all": true, "users": true, "reports": true, "settings": true}', true),
('dr_admin', 'dr.admin@smartclinic.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdJMReUHlpSLTEiHQ2XrcXztA/K', 'Medical', 'Administrator', 'ADMIN', '{"doctors": true, "appointments": true, "patients": true}', true),
('support', 'support@smartclinic.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdJMReUHlpSLTEiHQ2XrcXztA/K', 'Support', 'Team', 'SUPPORT', '{"appointments": true, "patients": false}', true);

-- Insert sample doctors
INSERT INTO doctors (email, password_hash, first_name, last_name, specialization, license_number, phone_number, bio, years_experience, consultation_fee, office_location, working_hours_start, working_hours_end, profile_image_url, is_active) VALUES
('dr.johnson@smartclinic.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdJMReUHlpSLTEiHQ2XrcXztA/K', 'Sarah', 'Johnson', 'Internal Medicine', 'MD123456', '+1-555-123-4567', 'Experienced internal medicine physician with expertise in preventive care and chronic disease management.', 15, 150.00, 'Room 201, Main Building', '09:00:00', '17:00:00', '/images/doctors/dr_johnson.jpg', true),
('dr.smith@smartclinic.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdJMReUHlpSLTEiHQ2XrcXztA/K', 'Michael', 'Smith', 'Cardiology', 'MD789012', '+1-555-234-5678', 'Board-certified cardiologist specializing in interventional cardiology and heart disease prevention.', 12, 200.00, 'Room 305, Cardiology Wing', '08:00:00', '16:00:00', '/images/doctors/dr_smith.jpg', true),
('dr.williams@smartclinic.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdJMReUHlpSLTEiHQ2XrcXztA/K', 'Emily', 'Williams', 'Pediatrics', 'MD345678', '+1-555-345-6789', 'Dedicated pediatrician with a passion for child health and development. Fluent in English and Spanish.', 8, 130.00, 'Room 102, Pediatric Wing', '10:00:00', '18:00:00', '/images/doctors/dr_williams.jpg', true),
('dr.brown@smartclinic.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdJMReUHlpSLTEiHQ2XrcXztA/K', 'David', 'Brown', 'Orthopedic Surgery', 'MD901234', '+1-555-456-7890', 'Orthopedic surgeon with expertise in sports medicine and joint replacement surgery.', 20, 250.00, 'Room 401, Surgery Wing', '07:00:00', '15:00:00', '/images/doctors/dr_brown.jpg', true),
('dr.davis@smartclinic.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdJMReUHlpSLTEiHQ2XrcXztA/K', 'Jennifer', 'Davis', 'Dermatology', 'MD567890', '+1-555-567-8901', 'Dermatologist specializing in medical and cosmetic dermatology with advanced training in Mohs surgery.', 10, 175.00, 'Room 203, Dermatology Suite', '09:30:00', '17:30:00', '/images/doctors/dr_davis.jpg', true);

-- Insert sample patients
INSERT INTO patients (email, password_hash, first_name, last_name, phone_number, date_of_birth, gender, address, emergency_contact_name, emergency_contact_phone, blood_type, allergies, medical_history, is_active) VALUES
('john.smith@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdJMReUHlpSLTEiHQ2XrcXztA/K', 'John', 'Smith', '+1-555-111-2222', '1980-05-15', 'MALE', '123 Main St, New York, NY 10001', 'Jane Smith', '+1-555-111-3333', 'O+', 'Penicillin, Shellfish', 'Hypertension diagnosed in 2015. Family history of heart disease.', true),
('mary.jones@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdJMReUHlpSLTEiHQ2XrcXztA/K', 'Mary', 'Jones', '+1-555-222-3333', '1992-11-22', 'FEMALE', '456 Oak Ave, Brooklyn, NY 11201', 'Robert Jones', '+1-555-222-4444', 'A-', 'No known allergies', 'Annual checkups with no significant medical history.', true),
('robert.wilson@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdJMReUHlpSLTEiHQ2XrcXztA/K', 'Robert', 'Wilson', '+1-555-333-4444', '1965-08-03', 'MALE', '789 Pine St, Queens, NY 11375', 'Linda Wilson', '+1-555-333-5555', 'B+', 'Aspirin, Latex', 'Type 2 Diabetes since 2010. History of knee surgery in 2018.', true),
('lisa.garcia@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdJMReUHlpSLTEiHQ2XrcXztA/K', 'Lisa', 'Garcia', '+1-555-444-5555', '1988-02-14', 'FEMALE', '321 Elm St, Manhattan, NY 10025', 'Carlos Garcia', '+1-555-444-6666', 'AB+', 'No known allergies', 'Pregnant - second trimester. Regular prenatal care.', true),
('james.taylor@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdJMReUHlpSLTEiHQ2XrcXztA/K', 'James', 'Taylor', '+1-555-555-6666', '2010-12-08', 'MALE', '654 Maple Dr, Bronx, NY 10458', 'Susan Taylor', '+1-555-555-7777', 'O-', 'Peanuts, Tree Nuts', 'Asthma since age 5. Regular pediatric care.', true),
('susan.lee@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdJMReUHlpSLTEiHQ2XrcXztA/K', 'Susan', 'Lee', '+1-555-666-7777', '1975-09-30', 'FEMALE', '987 Cedar Ave, Staten Island, NY 10301', 'Michael Lee', '+1-555-666-8888', 'A+', 'Sulfa drugs', 'Migraine headaches. History of gallbladder surgery.', true);

-- Insert sample appointments
INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, duration_minutes, status, appointment_type, reason_for_visit, notes) VALUES
(1, 1, '2025-10-15', '10:00:00', 60, 'SCHEDULED', 'Annual Physical', 'Annual checkup and blood pressure monitoring', NULL),
(2, 3, '2025-10-16', '14:30:00', 45, 'CONFIRMED', 'Consultation', 'General health consultation', NULL),
(3, 2, '2025-10-17', '09:00:00', 90, 'SCHEDULED', 'Cardiology Consultation', 'Follow-up for diabetes management and heart health', NULL),
(4, 3, '2025-10-18', '11:00:00', 60, 'SCHEDULED', 'Prenatal Checkup', 'Second trimester prenatal examination', NULL),
(5, 3, '2025-10-19', '15:00:00', 30, 'CONFIRMED', 'Pediatric Checkup', 'Routine pediatric examination and vaccinations', NULL),
(6, 5, '2025-10-20', '13:00:00', 45, 'SCHEDULED', 'Dermatology Consultation', 'Skin lesion examination', NULL),
(1, 2, '2025-10-22', '10:30:00', 60, 'SCHEDULED', 'Cardiology Follow-up', 'Blood pressure follow-up and medication review', NULL),
(2, 1, '2025-10-25', '16:00:00', 30, 'SCHEDULED', 'Follow-up', 'Lab results review', NULL);

-- Insert sample doctor availability (unavailable times)
INSERT INTO doctor_availability (doctor_id, date, start_time, end_time, availability_type, reason, is_recurring, recurrence_pattern) VALUES
(1, '2025-10-21', '12:00:00', '13:00:00', 'BREAK', 'Lunch break', true, 'daily'),
(1, '2025-10-23', '09:00:00', '17:00:00', 'UNAVAILABLE', 'Medical conference', false, NULL),
(2, '2025-10-24', '14:00:00', '16:00:00', 'UNAVAILABLE', 'Surgery scheduled', false, NULL),
(3, '2025-10-25', '12:30:00', '13:30:00', 'BREAK', 'Lunch break', true, 'daily'),
(4, '2025-10-26', '09:00:00', '17:00:00', 'UNAVAILABLE', 'Personal day off', false, NULL),
(5, '2025-10-27', '11:00:00', '12:00:00', 'BREAK', 'Administrative meeting', false, NULL);

-- Note: Password hash represents 'password123' encrypted with BCrypt
-- In a real application, passwords should be properly hashed and never stored in plain text
